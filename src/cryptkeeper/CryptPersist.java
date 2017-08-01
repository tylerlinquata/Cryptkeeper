package cryptkeeper;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created by u1068832 on 7/20/2017.
 */
public class CryptPersist implements Serializable {

    private byte[] salt;
    private byte[] iv;
    private String md5;


    public CryptPersist(byte[] salt, byte[] iv, String md5)
    {
        this.salt = salt;
        this.iv = iv;
        this.md5 = md5;
    }

    public void serialize(CryptPersist record) throws Exception
    {
        File myFile  = new File("data");
        List<CryptPersist> cList = new ArrayList<>();
        if(!myFile.isFile())
        {
            //add record
            cList.add(record);
            //create new file
            ObjectOutputStream os1 = new ObjectOutputStream(new FileOutputStream("data"));
            os1.writeObject(cList);
            os1.close();
        }
        else
        {
            //get current objects, add to list then write
            cList = deserialize();
            cList.add(record);
            //append to file
            ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("data", true)) {
                protected void writeStreamHeader() throws IOException {
                    reset();
                }
            };
            os2.writeObject(cList);
            os2.close();
        }

    }

    public List<CryptPersist> deserialize() throws Exception
    {
        List<CryptPersist> cpData = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data")))
        {
        // Read the next object from the stream. If there is none, the
        // EOFException will be thrown.
        // This call might also throw a ClassNotFoundException, which can be passed
        // up or handled here.
            while (true)
            {
                cpData = (List<CryptPersist>) in.readObject(); //add the persistence record
            }

        }
        catch (Exception e)
        {
            // If there are no more objects to read, return what we have.
            return cpData;
        }
    }

    public static String getFileChecksum(MessageDigest digest, File file) throws IOException
    {
        //Get file input stream for reading the file content
        FileInputStream fis = new FileInputStream(file);

        //Create byte array to read data in chunks
        byte[] byteArray = new byte[1024];
        int bytesCount = 0;

        //Read file data and update in message digest
        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        //close the stream; We don't need it now.
        fis.close();

        //Get the hash's bytes
        byte[] bytes = digest.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        //return complete hash
        return sb.toString();
    }

    @Override
    public String toString()
    {
        return String.format("salt: %s iv: %s md5: %s", salt.toString(), iv.toString(), md5.toString());
    }
}
