import java.util.*;
import java.io.*;

public class Document implements Serializable {
    List<Peripheral> peripherals = new ArrayList<Peripheral>();
    Variables variables = new Variables();
    Node head = new Node();

    public Document() {
        File dir = new File("Blocks");
        File [] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".fd"); 
            }
        });

        for (File fdFile : files) {
            peripherals.add(new Peripheral(fdFile.toString()));
        }
    }

    public List<Peripheral> getPeripherals() {
        return peripherals;
    }
}