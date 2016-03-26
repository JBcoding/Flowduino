import java.awt.datatransfer.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by mathias on 22/03/16.
 */
public class Node implements Serializable {
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

    public Node clone() {
        if (component != null) {
            return new Node(component.clone(), next);
        }
        return new Node();
    }

    public boolean isNodeSubNode(Node n) {
        if (n == this) {
            return true;
        }
        if (component != null) {
            if (component.getClass() == IfComponent.class) {
                IfComponent ifComponent = (IfComponent)component;
                return ifComponent.isNodeSubNode(n);
            } else if (component.getClass() == ForLoop.class || component.getClass() == WhileLoop.class) {
                Loop loop = (Loop)component;
                return loop.isNodeSubNode(n);
            }
        }
        return false;
    }
}
