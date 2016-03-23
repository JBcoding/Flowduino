import java.util.*;

public class GenericPeripheral {
    protected Peripheral parent;
    protected Map<String, String> variables;

    public GenericPeripheral(Peripheral parent) {
        variables = new HashMap<String, String>();
        for (Variable v : parent.getVariables()) {
            variables.put(v.toCode(), "");
        }
        this.parent = parent;
    }

    public void updateValueAtKey(String key, String newValue) {
        variables.remove(key);
        variables.put(key, newValue);
    }

    private String replaceFromHashmap(String s) {
        Iterator it = variables.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            s = s.replaceAll("\\{" + pair.getKey() + "\\}", pair.getValue().toString());
        }
        return s;
    }

    public String initCode() {
        String constructor = parent.getConstructor();
        constructor = replaceFromHashmap(constructor);
        return constructor + ";";
    }

    public String createFunctionCall(PeripheralFunction pf, List<Variable> parameters) {
        String call = pf.getCall();
        call = replaceFromHashmap(call);
        for (int i = 0; i < parameters.size(); i ++) {
            call = call.replaceAll("\\{" + i + "\\}", parameters.get(i).toCode());
        }
        return call + ";";
    }
}
