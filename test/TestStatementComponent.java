import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by mathias on 23/03/16.
 */
public class TestStatementComponent {

    @Test
    public void testStatement_SetterAndGetterValue_SetsAndGetsValue() {
        StatementComponent statement = new StatementComponent();

        String input = "if (æøå < 23) {a = 5}\n + 4";
        statement.setValue(input);

        assertEquals(statement.getValue(), input);
    }

    @Test
    public void testStatement_GetCodeSingleLine_InsertsSemicolon() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "i ++;");
    }

    @Test
    public void testStatement_GetCodeMultiLine_InsertsSemicolon() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++\nj++";
        statement.setValue(input);

        assertEquals(statement.toCode(0), "i ++;\nj++;");
    }

    @Test
    public void testStatement_GetCodeSingleLine_ReturnTabbedIndexedCode() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  i ++");
    }

    @Test
    public void testStatement_GetCodeMultiLine_ReturnTabbedIndexedCode() {
        StatementComponent statement = new StatementComponent();

        String input = "i ++\nj ++\nk ++";
        statement.setValue(input);

        assertEquals(statement.toCode(2), "  i ++\n  j ++\n  k ++");
    }

}
