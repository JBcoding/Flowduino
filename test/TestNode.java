import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;
/**
 * Created by mathias on 22/03/16.
 */

public class TestNode {
    @Test
    public void testNode_NextSetterAndGetter_SetsNext() {
        Node tested = new Node();
        Node next = new Node();
        tested.setNext(next);

        assertEquals(next, tested.getNext());
    }

    @Test
    public void testNode_ComponentSetterAndGetter_SetsComponent() {
        Node tested = new Node();
        IComponent statement = new StatementComponent();

        tested.setComponent(statement);

        assertEquals(tested.getComponent(), statement);
    }

    @Test
    public void testNode_EmptyConstructor_NextIsNull() {
        Node tested = new Node();

        assertNull(tested.getNext());
    }

    @Test
    public void testNode_EmptyConstructor_ComponentIsNull() {
        Node tested = new Node();

        assertNull(tested.getComponent());
    }

    @Test
    public void testNode_ConstructorWithComponent_SetsComponent() {
        IComponent statement = new StatementComponent();

        Node tested = new Node(statement);

        assertEquals(tested.getComponent(), statement);
    }

    @Test
    public void testNode_ConstructorWithComponent_NextIsNull() {
        IComponent statement = new StatementComponent();

        Node tested = new Node(statement);

        assertNull(tested.getNext());
    }

    @Test
    public void testNode_ConstructorWithComponentAndNext_SetsNext() {
        IComponent statement = new StatementComponent();

        Node nextNode = new Node();

        Node tested = new Node(statement, nextNode);

        assertEquals(tested.getNext(), nextNode);

    }

    @Test
    public void testNode_ConstructorWithComponentAndNext_SetsComponent() {
        IComponent statement = new StatementComponent();

        Node nextNode = new Node();

        Node tested = new Node(statement, nextNode);

        assertEquals(tested.getComponent(), statement);

    }
}
