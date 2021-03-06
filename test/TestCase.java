import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestCase {
    Case left;
    Case right;
    Case main;

    @Before
    public void SetUpVariables() {
        left = new Case(new Variable("i", "int"), new Constant(5), "==");
        right = new Case(new Variable("f", "string"), new Constant("\"hej\""), "!=");
        main = new Case(left, right, "||");
    }

    @Test
    public void TestCase_Constructor_LeftSideIsSet() {
        assertEquals(main.getLeftSide(), left);
    }

    @Test
    public void TestCase_Constructor_RightSideIsSet() {
        assertEquals(main.getRightSide(), right);
    }

    @Test
    public void TestCase_Constructor_OperatorIsSet() {
        assertEquals(main.getOperator(), "||");
    }


    @Test
    public void TestCase_ToCode_LeftSideFormatted() {
        assertEquals(left.toCode(), "(FV_i) == (5)");
    }

    @Test
    public void TestCase_ToCode_RightSideFormatted() {
        assertEquals(right.toCode(), "(FV_f) != (\"hej\")");
    }

    @Test
    public void TestCase_ToCode_MainFormatted() {
        assertEquals(main.toCode(), "((FV_i) == (5)) || ((FV_f) != (\"hej\"))");
    }
}
