package cryptkeeper;

import java.io.*;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author Team Loading...
 * Date: 7/23/2017
 */
public class CryptPersist implements Serializable {

    private String name;
    private String fileType;
    private byte[] salt;
    private byte[] iv;

    public byte[] getSalt() {
        return salt;
    }

    public byte[] getIv() {
        return iv;
    }

    public String getFileType() {
        return fileType;
    }


    public CryptPersist(String name, String fileType, byte[] salt, byte[] iv)
    {
        this.name = name;
        this.fileType = fileType;
        this.salt = salt;
        this.iv = iv;
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

    public static List<CryptPersist> deserialize()
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

    public static CryptPersist findMatch(String fileName)
    {
        List<CryptPersist> cp = deserialize();

        for (CryptPersist c : cp)
        {
            if(fileName.equals(c.name))
                return c;
        }

        return null;
    }
}
