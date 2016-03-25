import java.util.*;
import java.io.*;

public class Document implements Serializable {
    protected List<Peripheral> peripherals = new ArrayList<Peripheral>();
    protected Variables variables = new Variables();
    protected Node head = new Node();
    protected String name;

    public Document() {
        reloadPeripherals();
    }

    public void reloadPeripherals() {
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

    public Node getHead() {
        return head;
    }

    public void setHead(Node head) {
        this.head = head;
    }

    public Variables getVariables() {
        return variables;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}