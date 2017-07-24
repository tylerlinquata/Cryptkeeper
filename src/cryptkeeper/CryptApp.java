package cryptkeeper;

/**
 * Created by u1068832 on 7/20/2017.
 */
public class CryptApp {

    public static void main(String[] args)
    {
        System.out.println("Encrypting...");
        CryptKeeper crypt = new CryptKeeper();
        String msg;

        try
        {
            msg = crypt.Encrypt("myPassword", "C:\\crypt\\target.txt", true); //**update filepath to test
            System.out.println(msg);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        try
        {
            msg = crypt.Decrypt("myPassword", "C:\\crypt\\encryptedfile.des"); //**update filepath to test
            System.out.println(msg);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

}
