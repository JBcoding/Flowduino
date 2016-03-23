import java.util.*;

public abstract class ConditionalExecution implements IComponent {
    protected List<Node> headOfContents;

    public List<ICase> getHeadOfContents() {
        return null;
    }

    @Override
    public String toCode(int tabDepth) {
        return null;
    }
}