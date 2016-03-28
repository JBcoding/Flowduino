import java.io.Serializable;
import java.util.*;

public class PeripheralFunction implements Serializable {
    protected String name;
    protected String description;
    protected String call;
    protected List<Variable> parameters;
    protected Variable returnVar;

    public PeripheralFunction(Queue<String> lines) {
        this.name = lines.poll();
        parameters = new ArrayList<Variable>();
        while (lines.size() > 0) {
            String line = lines.peek();
            if (line.equals("[parameters]")) {
                lines.poll();
                while (lines.size() > 0 && lines.peek().indexOf("[") == -1) {
                    Scanner words = new Scanner(lines.poll());
                    String varType = words.next();
                    String varName = words.next();
                    parameters.add(new Variable(varName, varType));
                    words.close();
                }
            } else if (line.equals("[call]")) {
                lines.poll();
                call = lines.poll();
            } else if (line.equals("[return]")) {
                lines.poll();
                Scanner words = new Scanner(lines.poll());
                String varType = words.next();
                String varName = words.next();
                returnVar = new Variable(varName, varType);
                words.close();
            } else if (line.equals("[description]")) {
                lines.poll();
                description = lines.poll();
            } else {
                break;
            }
        }
    }

    public String getCall() {
        return call;
    }

    public String getName() {
        return name;
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public void setParameters(List<Variable> parameters) {
        this.parameters = parameters;
    }

    public String getDescription() {
        return description;
    }

    public Variable getReturnVar() {
        return returnVar;
    }

    public void setReturnVar(Variable returnVar) {
        this.returnVar = returnVar;
    }
}
