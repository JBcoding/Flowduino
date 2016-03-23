import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by mathias on 23/03/16.
 */
public class TestConstant {
    @Test
    public void TestConstant_Constructor_SetsValue() {
        Constant tested = new Constant(4);

        assertEquals(tested.toCode(), "4");

    }

    @Test
    public void TestConstant_ConstructorWithEmptyString_ThrowsArgumentException() {
        try {
            Constant tested = new Constant("");
            fail("Constant cannot have empty argument");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Argument must not be empty");
        }
    }

    @Test
    public void TestConstant_ConstructorWithNullArgument_ThrowsArgumentException() {
        try {
            Constant tested = new Constant(null);
            fail("Constant value must not be null");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Argument must not be null");
        }
    }

}
