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
        IComponent statement = new StatementComponent(null);

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
        IComponent statement = new StatementComponent(null);

        Node tested = new Node(statement);

        assertEquals(tested.getComponent(), statement);
    }

    @Test
    public void testNode_ConstructorWithComponent_NextIsNull() {
        IComponent statement = new StatementComponent(null);

        Node tested = new Node(statement);

        assertNull(tested.getNext());
    }

    @Test
    public void testNode_ConstructorWithComponentAndNext_SetsNext() {
        IComponent statement = new StatementComponent(null);

        Node nextNode = new Node();

        Node tested = new Node(statement, nextNode);

        assertEquals(tested.getNext(), nextNode);

    }

    @Test
    public void testNode_ConstructorWithComponentAndNext_SetsComponent() {
        IComponent statement = new StatementComponent(null);

        Node nextNode = new Node();

        Node tested = new Node(statement, nextNode);

        assertEquals(tested.getComponent(), statement);

    }

    @Test
    public void testNode_getProgramCodeWithStatements_ReturnsStatements() {
        Node program = new Node();
        DelayComponent delay = new DelayComponent();
        delay.setDelaySeconds(4);

        program.setComponent(delay);

        assertEquals(program.getProgramCode(2), delay.toCode(2));
    }

    @Test
    public void testNode_getProgramCodeWithMultipleStatements_ReturnsMultipleStatements() {
        DelayComponent delay1 = new DelayComponent();
        DelayComponent delay2 = new DelayComponent();
        delay1.setDelayMilliseconds(3);
        delay2.setDelayMilliseconds(5);

        Node tail = new Node(delay2);
        Node head = new Node(delay1, tail);

        assertEquals(head.getProgramCode(2), delay1.toCode(2) + "\n\n" + delay2.toCode(2));
    }
}
