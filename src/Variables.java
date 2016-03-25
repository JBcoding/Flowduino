import java.util.*;


/**
 * Created by mathias on 24/03/16.
 */
public class Variables {
    protected List<Variable> variables = new ArrayList<Variable>() {};

    public void Add(Variable variable) {
        if (exists(variable.getName())) {
            throw new IllegalArgumentException("Variable already exists");
        }
        variables.add(variable);
    }

    public boolean exists(String name) {
        return variables.stream().filter(v -> v.getName().equals(name)).count() > 0;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> newVariables) {
        variables = newVariables;
    }

    public void remove(int index) throws VariableNotFoundException {
        if (index >= variables.size()) {
            throw new VariableNotFoundException();
        }
        variables.remove(index);
    }

    public void remove(Variable var) throws VariableNotFoundException {
        if (!variables.contains(var)) {
            throw new VariableNotFoundException();
        }
        variables.remove(var);
    }

    public void remove(String name) throws VariableNotFoundException {
        if (!exists(name)) {
            throw new VariableNotFoundException();
        }
        variables.removeIf(v -> v.getName().equals(name));
    }

    public Variable get(int index) throws VariableNotFoundException {
        if (index >= variables.size()) {
            throw new VariableNotFoundException();
        }
        return variables.get(index);
    }

    public Variable get(String name) throws VariableNotFoundException {

        for (Variable var : variables) {
            if (var.getName().equals(name)) {
                return var;
            }
        }
        throw new VariableNotFoundException();
    }
}
