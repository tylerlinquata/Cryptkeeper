package cryptkeeper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * @author Team Loading...
 * Date: 7/23/2017
 */
public class CryptKeeper {

    public String Encrypt(char[] password, String filePath, Boolean keepFile) throws Exception
    {
        // file to be encrypted
        File target = new File(filePath);
        FileInputStream inFile = new FileInputStream(target);

        // set up file for encryption
        String newName = CryptUtil.formFileName(filePath, ".des"); //filename for new encrypted version
        FileOutputStream outFile = new FileOutputStream(newName);

        // salt is used for encoding
        // writing it to a file
        // salt should be transferred to the recipient securely
        // for decryption
        byte[] salt = createSalt();

        //create key
        SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password, salt, 65536,
                256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //cipher
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();

        // iv adds randomness to the text and just makes the mechanism more
        // secure
        // used while initializing the cipher
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();

        //file encryption
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }

        byte[] output = cipher.doFinal();
        if (output != null)
            outFile.write(output);

        //close and flush
        inFile.close();
        outFile.flush();
        outFile.close();

        //persist data
        String fileType = CryptUtil.extractExtension(filePath); //persist this for decryption
        CryptPersist cp = new CryptPersist(CryptUtil.extractFileName(filePath), fileType, salt, iv);
        cp.serialize(cp);

        if (!keepFile)
            CryptUtil.deleteFile(filePath);

        return "Success - file encrypted!";
    }

    public String Decrypt(char[] password, String filePath) throws Exception
    {
        String name = CryptUtil.extractFileName(filePath);
        CryptPersist cryptMatch = CryptPersist.findMatch(name);
        String status = "Decryption unable to be processed.";
        //proceed if we have a match
        if(cryptMatch != null)
        {
            SecretKeyFactory factory = SecretKeyFactory
                    .getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(password, cryptMatch.getSalt(), 65536,
                    256);
            SecretKey tmp = factory.generateSecret(keySpec);
            SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

            // file decryption
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(cryptMatch.getIv())); //use our iv match
            File target = new File(filePath);
            FileInputStream fis = new FileInputStream(target);

            String newName = CryptUtil.formFileName(filePath, cryptMatch.getFileType()); //filename for new encrypted version
            FileOutputStream fos = new FileOutputStream(newName);

            byte[] in = new byte[64];
            int read;
            while ((read = fis.read(in)) != -1) {
                byte[] output = cipher.update(in, 0, read);
                if (output != null)
                    fos.write(output);
            }

            byte[] output = cipher.doFinal();
            if (output != null)
                fos.write(output);
            fis.close();
            fos.flush();
            fos.close();

            //update status message to complete
            status = "Success! File Decrypted.";
        }

        return status;
    }

    private byte[] createSalt()
    {
        byte[] salt = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }
}

