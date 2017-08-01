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
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by u1068832 on 7/20/2017.
 */
public class CryptKeeper {

    /*TODO: write method to extract filename from filepath. should be able to locate the last index of \ and create
    *      substring containing filename. We will also need to exclude the filetype, as it will change
    */
    public String Encrypt(char[] password, String filePath, Boolean keepFile) throws Exception
    {
        // file to be encrypted
        File target = new File(filePath);
        FileInputStream inFile = new FileInputStream(target);

        //get md5 checksum, we will write the record to a file using cryptpersist
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        String checksum = CryptPersist.getFileChecksum(md5Digest, target);
        System.out.printf("Checksum = %s\n", checksum);

        //get new name
        String newName = extractFileName(filePath);
        String nPath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
        // encrypted file
        FileOutputStream outFile = new FileOutputStream(nPath + newName + ".des");

        // password, iv and salt should be transferred to the other end
        // in a secure manner

        // salt is used for encoding
        // writing it to a file
        // salt should be transferred to the recipient securely
        // for decryption
        byte[] salt = createSalt();

        SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password, salt, 65536,
                256);
        SecretKey secretKey = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        //
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();

        // iv adds randomness to the text and just makes the mechanism more
        // secure
        // used while initializing the cipher
        // file to store the iv
        FileOutputStream ivOutFile = new FileOutputStream("iv.enc");
        byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        ivOutFile.write(iv);
        ivOutFile.close();

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

        inFile.close();
        outFile.flush();
        outFile.close();

        if (!keepFile)
            deleteFile(filePath);
        //persist data
        CryptPersist cp = new CryptPersist(salt, iv, checksum);
        cp.serialize(cp);

        List<CryptPersist>  listPersist = cp.deserialize();
        for (CryptPersist c : listPersist)
        {
            System.out.println(c.toString());
        }

        return "Success - file encrypted!";
    }

    public String Decrypt(char[] password, String filePath) throws Exception
    {
        // reading the salt
        // user should have secure mechanism to transfer the
        // salt, iv and password to the recipient
        FileInputStream saltFis = new FileInputStream("salt.enc");
        byte[] salt = new byte[8];
        saltFis.read(salt);
        saltFis.close();

        // reading the iv
        FileInputStream ivFis = new FileInputStream("iv.enc");
        byte[] iv = new byte[16];
        ivFis.read(iv);
        ivFis.close();

        SecretKeyFactory factory = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        KeySpec keySpec = new PBEKeySpec(password, salt, 65536,
                256);
        SecretKey tmp = factory.generateSecret(keySpec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");

        // file decryption
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
        File target = new File(filePath);
        FileInputStream fis = new FileInputStream(target);

        String newName = extractFileName(filePath);
        String nPath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);

        FileOutputStream fos = new FileOutputStream(nPath + newName + ".txt");
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

        return "Success! File Decrypted.";
    }

    private byte[] createSalt()
    {
        byte[] salt = new byte[8];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        try
        {
            FileOutputStream saltOutFile = new FileOutputStream("salt.enc");
            saltOutFile.write(salt);
            saltOutFile.close();
        }
        catch(Exception x)
        {
            System.err.println(x);
        }
        return salt;
    }

    private void deleteFile(String filePath)
    {
        try
        {
            Path myPath = Paths.get(filePath);
            Files.delete(myPath);
        }
        catch (Exception x) {
            System.err.println(x.getMessage());
        }
    }

    /**
     * This method takes in a file path and returns the specific file name without an extension.
     * @param filePath the file path in which contains the desired file name.
     * @return a String of the filename without an extension.
     */
    private String extractFileName (String filePath)
    {
        String list[] = filePath.split("\\\\"); // form an array containing all info between backslashes
        String filename = list[list.length - 1]; // take the last item in the array
        return filename.substring(0, filename.lastIndexOf('.')); // return the file without extension
    }

}

