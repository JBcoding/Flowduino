/**
 * Created by mathias on 22/03/16.
 */
public class Node {
    protected Node next;
    protected IComponent component;

    public Node() {}

    public Node(IComponent component) {
        this.component = component;
    }

    public Node(IComponent component, Node next) {
        this.component = component;
        this.next = next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public void setComponent(IComponent component) {
        this.component = component;
    }

    public IComponent getComponent() {
        return component;
    }

    public String getProgramCode(int tabDepth) {
        if (component == null) {
            return "";
        }
        if (next == null) {
            return component.toCode(tabDepth);
        } else {
            return component.toCode(tabDepth) + "\n\n" + next.getProgramCode(tabDepth);
        }
    }

}
