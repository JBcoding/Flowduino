import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Created by mathias on 23/03/16.
 */
public final class Settings {
    public static int getTabDepth() {
        return 2;
    }

    public static String getTabString(int tabDepth) {
        String tab = "";
        for (int i = 0; i < tabDepth; i ++) {
            tab += " ";
        }
        return tab;
    }

    public static void saveFile(String content, String fileName) {
        try {
            byte[] b = content.getBytes(StandardCharsets.UTF_8);

            OutputStream out = new FileOutputStream(new File(fileName));
            out.write(b);
            out.close();
        } catch (Exception e) {
            // Something unexpected happend
            System.out.println("Something unexpected happend in the saveFile method in Storage class");
        }
    }

    public static String loadFile(String fileName) { // retrun null if an error occurs
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                // Dosn't exist
                System.out.println("The file \"" + fileName + "\" dosn't exist");
                return null;
            } else if (f.isDirectory()) {
                // we got a folder
                System.out.println("The file \"" + fileName + "\" is a folder");
                return null;
            }

            Scanner input = new Scanner(f);
            String fileContent = "";

            // The fence post problem, with "\n"
            if (input.hasNextLine()) {
                fileContent = input.nextLine();
            } else {
                return ""; // it's empty, but there were no errors
            }

            while (input.hasNextLine()) {
                fileContent += "\n" + input.nextLine();
            }

            input.close();
            return fileContent;

        } catch (Exception e) {
            // Something unexpected happend
            System.out.println("Something unexpected happend in the loadFile method in Storage class");
            return null;
        }
    }
}
