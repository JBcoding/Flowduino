import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestIfComponent {
    Case mainCase;
    Case secondCase;
    Node head;
    IfComponent ifComponent1;
    IfComponent ifComponent2;
    IfComponent ifComponent3;
    IfComponent ifComponent4;

    @Before
    public void SetUpVariables() {
        mainCase = new Case(new Variable("i", "int"), new Constant(5), "==");
        secondCase = new Case(new Variable("i", "int"), new Constant(6), "==");


        DelayComponent delay1 = new DelayComponent();
        DelayComponent delay2 = new DelayComponent();
        delay1.setDelayMilliseconds(3);
        delay2.setDelayMilliseconds(5);

        Node tail = new Node(delay2);
        head = new Node(delay1, tail);


        ifComponent1 = new IfComponent(new ArrayList<Node>() {{add(head);}}, new ArrayList<ICase>() {{add(mainCase);}});
        ifComponent2 = new IfComponent(new ArrayList<Node>() {{add(head);add(head);}}, new ArrayList<ICase>() {{add(mainCase);add(secondCase);}});
        ifComponent3 = new IfComponent(new ArrayList<Node>() {{add(head);add(head);}}, new ArrayList<ICase>() {{add(mainCase);}});
        ifComponent4 = new IfComponent(new ArrayList<Node>() {{add(head);add(head);add(head);}}, new ArrayList<ICase>() {{add(mainCase);add(secondCase);}});
    }

    @Test
    public void TestIfComponent_ToCodeOneIf_CodeIsFormatted() {
        assertEquals(ifComponent1.toCode(2), "  if ((FV_i) == (5)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }

    @Test
    public void TestIfComponent_ToCodeOneIfAndOneElseIf_CodeIsFormatted() {
        assertEquals(ifComponent2.toCode(2), "  if ((FV_i) == (5)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  } else if ((FV_i) == (6)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }

    @Test
    public void TestIfComponent_ToCodeOneIfAndOneElse_CodeIsFormatted() {
        assertEquals(ifComponent3.toCode(2), "  if ((FV_i) == (5)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  } else {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }

    @Test
    public void TestIfComponent_ToCodeOneIfAndOneElseIfAndOneElse_CodeIsFormatted() {
        assertEquals(ifComponent4.toCode(2), "  if ((FV_i) == (5)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  } else if ((FV_i) == (6)) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  } else {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }
}
