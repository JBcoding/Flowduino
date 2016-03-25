import org.junit.Test;
import org.omg.PortableServer.POAManagerPackage.State;

import java.util.ArrayList;

import static org.junit.Assert.*;
/**
 * Created by mathias on 23/03/16.
 */
public class TestStatementComponent {


    @Test
    public void testStatement_SetterAndGetterValue_SetsAndGetsValue() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i = 4 + 3\nj = 4 + i";
        statement.setValue(input);

        assertEquals(statement.getValue(), input);
    }

    @Test
    public void testStatement_NoPrefixesSingleLine_InsertsPrefixes() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;");
    }

    @Test
    public void testStatement_NoPrefixesMultiLine_InsertsPrefixes() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++\nj ++\nk ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;\nFV_j ++;\nFV_k ++;");
    }

    @Test
    public void testStatement_GetCodeSingleLine_InsertsSemicolon() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_InsertsSemicolon() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++\nj++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;\nFV_j++;");
    }

    @Test
    public void testStatement_GetCodeSingleLine_ReturnTabbedIndexedCode() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_i ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCode() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i ++\nj ++\nk ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_i ++;\n  FV_j ++;\n  FV_k ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCodeWithString() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "stringI = \"stringI\"";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_stringI = \"stringI\";");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCodeWithMultipleSameNameVariable() throws MalformedExpressionException {
        StatementComponent statement = new StatementComponent(null);

        String input = "i = sqrt(b)\ni = pow(i, 3)";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_i = sqrt(FV_b);\n  FV_i = pow(FV_i, 3);");
    }

    @Test
    public void testStatement_MalformedSingleLineStatement_ThrowsException() {
        Variables variables = new Variables();
        variables.setVariables(new ArrayList<Variable>() {{
            add(new Variable("i", "string", "teststring"));
            add(new Variable("j", "string", "anotherTestsstring"));
        }});
        StatementComponent statement = new StatementComponent(variables);

        String input = "j = 4";

        try {
            statement.setValue(input);
            fail("Accepted invalid string input");
        } catch (MalformedExpressionException e) {
            assertEquals(e.getMessage(), "Argument not string");
        }
    }

    @Test
    public void testStatement_MalformedMultiLineStatement_ThrowsException() {
        Variables variables = new Variables();
        variables.setVariables(new ArrayList<Variable>() {{
            add(new Variable("i", "string", "teststring"));
            add(new Variable("j", "string", "anotherTestsstring"));
        }});
        StatementComponent statement = new StatementComponent(variables);

        String input = "j = 4\ni = \"stringinput\"hej";

        try {
            statement.setValue(input);
            fail("Accepted invalid string input");
        } catch (MalformedExpressionException e) {
            assertEquals(e.getMessage(), "Invalid string input");
        }
    }

    @Test
    public void testStatement_MalformedSingleLineStatementIntToString_ThrowsException() {
        Variables variables = new Variables();
        variables.setVariables(new ArrayList<Variable>() {{
            add(new Variable("i", "int", "4"));
        }});
        StatementComponent statement = new StatementComponent(variables);

        String input = "i = \"test\"";

        try {
            statement.setValue(input);
            fail("Accepted string input as int");
        } catch (MalformedExpressionException e) {
            assertEquals(e.getMessage(), "Invalid int input");
        }
    }
}
