import java.util.*;

public abstract class ConditionalExecution implements IComponent {
    protected List<Node> headOfContents;

    public List<Node> getHeadOfContents() {
        return headOfContents;
    }
}