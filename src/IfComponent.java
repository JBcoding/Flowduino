import java.util.*;

public class IfComponent extends ConditionalExecution {
    protected List<ICase> cases;

    public IfComponent(List<Node> headOfContents, List<ICase> cases) {
        this.headOfContents = headOfContents;
        this.cases = cases;
    }

    public List<ICase> getCases() {
        return cases;
    }

    @Override
    public String toCode(int tabDepth) {
        String code = "";
        String tab = Settings.getTabString(tabDepth);
        code = tab;
        for (int i = 0; i < cases.size(); i ++) {
            code += ((i != 0) ? " else " : "") + "if (" + cases.get(i).toCode() + ") {\n" + headOfContents.get(i).getProgramCode(tabDepth + Settings.getTabDepth()) + "\n" + tab + "}";
        }
        if (cases.size() < headOfContents.size()) {
            code += " else {\n" + headOfContents.get(headOfContents.size() - 1).getProgramCode(tabDepth + Settings.getTabDepth()) + "\n" + tab + "}";
        }
        return code;
    }
}