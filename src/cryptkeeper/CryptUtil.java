package cryptkeeper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Team Loading...
 * Date: 7/30/2017
 */
public class CryptUtil {

    /**
     * given a path to a file it returns a unique file name relative to the file also in that
     * directory
     * @param filePath a path to a file as a string
     * @param fileType the desired suffix, ex. ".jpg" or ".txt"
     * @return unique file name with the format "filename (_).*extension*"
     */
    public static String formFileName(String filePath, String fileType) {
        if(!fileType.contains("."))
            fileType = "." + fileType; //prepend . if not present

        String filename = extractFileName(filePath);
        File folder = new File(filePath.replace(filename + filePath.substring(filePath.lastIndexOf('.'),
                filePath.length()), "")); //remove filename and extension
        File[] listOfFiles = folder.listFiles();

        boolean fileNameExists = checkForFile(filename + fileType, listOfFiles);

        int fileCounter = 1;
        while (fileNameExists) {
            if (!(checkForFile((filename + " (" + fileCounter + ")" + fileType), listOfFiles))) {
                return String.format("%s\\%s (%d)%s", folder, filename, fileCounter, fileType);
            }
            fileCounter++;
        }
        return String.format("%s\\%s%s", folder, filename, fileType);
    }

    /**
     * checks for a file with a given name from a list of files
     * @param filename the string name
     * @param listOfFiles an array of files
     * @return true if file is found with that name
     */
    private static boolean checkForFile(String filename, File[] listOfFiles) {
        for (File f :
                listOfFiles) {
            if (filename.equals(f.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method takes in a file path and returns the specific file name without an extension.
     * @param filePath the file path in which contains the desired file name.
     * @return a String of the filename without an extension.
     */
    public static String extractFileName(String filePath)
    {
        String list[] = filePath.split("\\\\"); // form an array containing all info between backslashes
        String filename = list[list.length - 1]; // take the last item in the array
        return filename.substring(0, filename.lastIndexOf('.')); // return the file without extension
    }

    public static String extractExtension(String filePath)
    {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    public static void deleteFile(String filePath)
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
}
