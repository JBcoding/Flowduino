import java.util.*;

public class StatementComponent implements IComponent{
    String value;

    public StatementComponent() {}

    public StatementComponent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toCode(int tabDepth) {
        String valueCopy = new String(value);
        valueCopy = valueCopy.replaceAll("\\\".*?\\\"", " ");
        valueCopy = valueCopy.replaceAll("\\'.*?\\'", " ");
        valueCopy = valueCopy.replace("pow(", " ");
        valueCopy = valueCopy.replace("sqrt(", " ");
        valueCopy = valueCopy.replace("(", " ");
        valueCopy = valueCopy.replace(")", " ");
        valueCopy = valueCopy.replace("+", " ");
        valueCopy = valueCopy.replace("-", " ");
        valueCopy = valueCopy.replace("*", " ");
        valueCopy = valueCopy.replace("/", " ");
        valueCopy = valueCopy.replace("%", " ");
        valueCopy = valueCopy.replace("=", " ");
        valueCopy = valueCopy.replace(".", " ");
        valueCopy = valueCopy.replace(",", " ");
        valueCopy = valueCopy.replace("\n", " ");
        valueCopy += " ";
        valueCopy = valueCopy.replaceAll("-?\\d+", " ");
        while (valueCopy.indexOf("  ") != -1) {
            valueCopy = valueCopy.replace("  ", " ");
        }
        String[] variabelsArray = valueCopy.split(" ");
        Set<String> variabels = new HashSet<String>(Arrays.asList(variabelsArray));
        valueCopy = new String(value);
        for (String s : variabels) {
            valueCopy = valueCopy.replaceAll(s + "(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)", "FV_" + s);
        }
        String[] lines = valueCopy.split("\n");
        String code = "";
        String tab = Settings.getTabString(tabDepth);
        for (int i = 0; i < lines.length; i ++) {
            code += tab + lines[i] + ";\n";
        }
        return code.substring(0, code.length()-1);
    }
}