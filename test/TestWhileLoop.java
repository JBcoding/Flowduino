import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestWhileLoop {
    Case mainCase;
    Node head;
    WhileLoop whileLoop;

    @Before
    public void SetUpVariables() {
        mainCase = new Case(new Variable("i", "int"), new Constant(5), "==");


        DelayComponent delay1 = new DelayComponent();
        DelayComponent delay2 = new DelayComponent();
        delay1.setDelayMilliseconds(3);
        delay2.setDelayMilliseconds(5);

        Node tail = new Node(delay2);
        head = new Node(delay1, tail);


        whileLoop = new WhileLoop(mainCase);
    }

    @Test
    public void TestWhileLoop_Constructor_ConditionIsSet() {
        assertEquals(whileLoop.getCondition(), mainCase);
    }


    @Test
    public void TestWhileLoop_SetterAndGetterContentHead_ContentHeadIsSet() {
        whileLoop.setHeadOfContent(head);
        assertEquals(whileLoop.getHeadOfContent(), head);
    }

    @Test
    public void TestWhileLoop_SetterAndGetterContentHead_ConditionIsSet() {
        Case newCase = new Case(new Constant(2), new Constant(3), "!=");
        whileLoop.setCondition(newCase);
        assertEquals(whileLoop.getCondition(), newCase);
    }


    @Test
    public void TestWhileLoop_ToCode_CodeIsFormatted() {
        whileLoop.setHeadOfContent(head);
        assertEquals(whileLoop.toCode(2), "  while (i == 5) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }
}
