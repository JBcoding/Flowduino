import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mathias on 23/03/16.
 */


public class TestVariable {
    Variable FullConstructor;
    Variable NotFullConstructor;
    Variable EmptyValueConstructor;
    @Before
    public void SetUpVariables() {
        FullConstructor = new Variable("i", "int", "14");
        NotFullConstructor = new Variable("j", "int");
        EmptyValueConstructor = new Variable("j", "int", "");

    }
    @Test
    public void TestVariable_ConstructorWithoutStartValue_StartValueIsNull() {
        assertNull(NotFullConstructor.getStartValue());
    }

    @Test
    public void TestVariable_ConstructorWithStartValue_StartValueIsNotNull() {
        assertNotNull(FullConstructor.getStartValue());
    }

    @Test
    public void TestVariable_ConstructorWithEmptyStartValue_StartValueIsNull() {
        assertNull(EmptyValueConstructor.getStartValue());
    }
    @Test
    public void TestVariable_ConstructorWithNullName_ThrowsException() {
        try {
            new Variable(null, "int");
            fail("Null name didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name cannot be null");
        }
    }

    @Test
    public void TestVariable_ConstructorWithNullValue_ThrowsException() {
        try {
            new Variable("name", "int", null);
            fail("Null value didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Value cannot be null");
        }
    }

    @Test
    public void TestVariable_ConstructorWithNullType_ThrowsException() {
        try {
            new Variable("name", null);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Type cannot be null");
        }
    }

    @Test
    public void TestVariable_nameSetterNullArg_ThrowsException() {
        try {
            FullConstructor.setName(null);
            fail("setName with null argument didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Name cannot be null");
        }
    }

    @Test
    public void TestVariable_TypeSetterNullArg_ThrowsException() {
        try {
            FullConstructor.setType(null);
            fail("setType with null argument didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Type cannot be null");
        }
    }


    @Test
    public void TestVariable_SetterAndGetter_SetsName() {
        Variable tested = new Variable("a", "string", "abc");

        tested.setName("b");

        assertEquals(tested.getName(), "b");
    }

    @Test
    public void TestVariable_SetterAndGetter_SetsValue() {
        Variable tested = new Variable("a", "string", "abc");

        tested.setStartValue("b");

        assertEquals(tested.getStartValue(), "b");
    }

    @Test
    public void TestVariable_SetterAndGetter_SetsType() {
        Variable tested = new Variable("a", "string", "abc");

        tested.setType("int");

        assertEquals(tested.getType(), "int");
    }

    @Test
    public void TestVariable_toCode_InsertsPrefix() {
        Variable tested = new Variable("a", "string", "abc");

        assertEquals(tested.toCode(), "FV_a");
    }

    @Test
    public void TestVariable_initCodeWithString_InsertsPrefixAndWrapsString() {
        Variable tested = new Variable("a", "string", "abc");

        assertEquals(tested.getInitCode(), "string FV_a = \"abc\";");
    }

    @Test
    public void TestVariable_initCodeWithChar_InsertsPrefixAndWrapsChar() {
        Variable tested = new Variable("a", "char", "'a'");

        assertEquals(tested.getInitCode(), "char FV_a = 'a';");
    }

    @Test
    public void TestVariable_initCodeWithNumber_InsertsPrefix() {
        Variable tested = new Variable("a", "int", "123");

        assertEquals(tested.getInitCode(), "int FV_a = 123;");
    }

    @Test
    public void TestVariable_initCodeWithStringWithoutStartValue_InsertsPrefixAndWrapsString() {
        Variable tested = new Variable("a", "string");

        assertEquals(tested.getInitCode(), "string FV_a;");
    }

    @Test
    public void TestVariable_initCodeWithCharWithoutStartValue_InsertsPrefixAndWrapsChar() {
        Variable tested = new Variable("a", "char");

        assertEquals(tested.getInitCode(), "char FV_a;");
    }

    @Test
    public void TestVariable_initCodeWithNumberWithoutStartValue_InsertsPrefix() {
        Variable tested = new Variable("a", "int");

        assertEquals(tested.getInitCode(), "int FV_a;");
    }
}
