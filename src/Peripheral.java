import javax.lang.model.element.VariableElement;
import java.util.List;
import java.io.*;
import java.util.*;

public class Peripheral implements Serializable {
    protected String name;
    protected String title;
    protected List<String> imports;
    protected List<Variable> variables;
    protected List<String> otherConnections;
    protected String constructor;
    protected List<PeripheralFunction> functions;

    public Peripheral(String path) {
        imports = new ArrayList<String>();
        variables = new ArrayList<Variable>();
        otherConnections = new ArrayList<String>();
        functions = new ArrayList<PeripheralFunction>();

        Queue<String> lines = new LinkedList<String>();
        try {
            Scanner input = new Scanner(new File(path));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                while (line.length() > 0 && line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                lines.add(line);
            }
            input.close();
        } catch (FileNotFoundException e) {}


        name = lines.poll();
        while (lines.size() > 0) {
            String line = lines.poll();
            if (line.equals("[imports]")) {
                while (lines.size() > 0 && lines.peek().indexOf("[") == -1) {
                    imports.add(lines.poll());
                }
            } else if (line.equals("[title]")) {
                title = lines.poll();
            } else if (line.equals("[variables]")) {
                while (lines.size() > 0 && lines.peek().indexOf("[") == -1) {
                    Scanner words = new Scanner(lines.poll());
                    String varType = words.next();
                    String varName = words.next();
                    variables.add(new Variable(varName, varType));
                    words.close();
                }
            } else if (line.equals("[power]")) {
                while (lines.size() > 0 && lines.peek().indexOf("[") == -1) {
                    otherConnections.add(lines.poll());
                }
            } else if (line.equals("[constructor]")) {
                constructor = lines.poll();
            } else if (line.equals("[functions]")) {
                while (lines.size() > 0 && lines.peek().indexOf("[") == -1) {
                    functions.add(new PeripheralFunction(lines));
                }
            }
        }
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public String getConstructor() {
        return constructor;
    }

    public List<PeripheralFunction> getFunctions() {
        return functions;
    }

    public String getName() {
        return name;
    }
}