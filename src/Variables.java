import java.util.*;


/**
 * Created by mathias on 24/03/16.
 */
public final class Variables {
    protected static List<Variable> variables = new ArrayList<Variable>() {};

    public static void Add(Variable variable) {
        if (exists(variable.getName())) {
            throw new IllegalArgumentException("Variable already exists");
        }
        variables.add(variable);
    }

    public static boolean exists(String name) {
        return variables.stream().filter(v -> v.getName().equals(name)).count() > 0;
    }

    public static List<Variable> getVariables() {
        return variables;
    }

    public static void setVariables(List<Variable> newVariables) {
        variables = newVariables;
    }

    public static void remove(int index) throws VariableNotFoundException {
        if (index >= variables.size()) {
            throw new VariableNotFoundException();
        }
        variables.remove(index);
    }

    public static void remove(Variable var) throws VariableNotFoundException {
        if (!variables.contains(var)) {
            throw new VariableNotFoundException();
        }
        variables.remove(var);
    }

    public static void remove(String name) throws VariableNotFoundException {
        if (!exists(name)) {
            throw new VariableNotFoundException();
        }
        variables.removeIf(v -> v.getName().equals(name));
    }

    public static Variable get(int index) throws VariableNotFoundException {
        if (index >= variables.size()) {
            throw new VariableNotFoundException();
        }
        return variables.get(index);
    }

    public static Variable get(String name) throws VariableNotFoundException {

        for (Variable var : variables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        throw new VariableNotFoundException();
    }
}
