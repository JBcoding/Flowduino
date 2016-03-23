import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by mathias on 23/03/16.
 */
public class TestStatementComponent {


    @Test
    public void testStatement_SetterAndGetterValue_SetsAndGetsValue() {
        StatementComponent statement = new StatementComponent();

        String input = "i = 4 + 3\nj = 4 + i";
        statement.setValue(input);

        assertEquals(statement.getValue(), input);
    }

    @Test
    public void testStatement_NoPrefixesSingleLine_InsertsPrefixes() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;");
    }

    @Test
    public void testStatement_NoPrefixesMultiLine_InsertsPrefixes() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++\nj ++\nk ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;\nFV_j ++;\nFV_k ++;");
    }

    @Test
    public void testStatement_GetCodeSingleLine_InsertsSemicolon() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_InsertsSemicolon() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++\nj++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "FV_i ++;\nFV_j++;");
    }

    @Test
    public void testStatement_GetCodeSingleLine_ReturnTabbedIndexedCode() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_i ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCode() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++\nj ++\nk ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_i ++;\n  FV_j ++;\n  FV_k ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCodeWithString() {
        StatementComponent statement = new StatementComponent();

        String input = "stringI = \"stringI\"";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  FV_stringI = \"stringI\";");
    }



}
