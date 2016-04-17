import java.util.*;

public abstract class ConditionalExecution implements IComponent {
    protected List<Node> headOfContents;

    public List<Node> getHeadOfContents() {
        return headOfContents;
    }

    public void setHeadOfContents(List<Node> headOfContents) {
        this.headOfContents = headOfContents;
    }

    public abstract IComponent clone();
}