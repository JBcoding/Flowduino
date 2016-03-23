/**
 * Created by mathias on 23/03/16.
 */
public class StatementComponent implements IComponent{
    String value;

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
        valueCopy = valueCopy.replace("\n", " ");
        while (valueCopy.indexOf("  ") != -1) {
            valueCopy = valueCopy.replace("  ", " ");
        }
        String[] variabels = valueCopy.split(" ");
        valueCopy = new String(value);
        for (int i = 0; i < variabels.length; i ++) {
            valueCopy = valueCopy.replaceAll(variabels[i] + "(?=([^\"']*[\"'][^\"']*[\"'])*[^\"']*$)", "FV_" + variabels[i]);
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