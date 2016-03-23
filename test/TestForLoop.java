import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestForLoop {
    Node head;
    Variable counter;
    IValues endValue;
    IValues startValue;
    IValues stepValue;

    @Before
    public void SetUpVariables() {
        DelayComponent delay1 = new DelayComponent();
        DelayComponent delay2 = new DelayComponent();
        delay1.setDelayMilliseconds(3);
        delay2.setDelayMilliseconds(5);

        Node tail = new Node(delay2);
        head = new Node(delay1, tail);
        counter = new Variable("i", "int");
        endValue = new Constant(40);
        startValue = new Constant(4);
        stepValue = new Constant(2);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_CounterIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue);
        assertEquals(forLoop.getCounter(), counter);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_EndValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue);
        assertEquals(forLoop.getEndValue(), endValue);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_StartValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue);
        assertEquals(forLoop.getStartValue().toCode(), (new Constant(0)).toCode());
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_StepValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue);
        assertEquals(forLoop.getStep().toCode(), (new Constant(1)).toCode());
    }


    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_CounterIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue, stepValue);
        assertEquals(forLoop.getCounter(), counter);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_EndValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue, stepValue);
        assertEquals(forLoop.getEndValue(), endValue);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_StartValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue, stepValue);
        assertEquals(forLoop.getStartValue().toCode(), (new Constant(0)).toCode());
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_StepValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, endValue, stepValue);
        assertEquals(forLoop.getStep(), stepValue);
    }


    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_CounterIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        assertEquals(forLoop.getCounter(), counter);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_EndValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        assertEquals(forLoop.getEndValue(), endValue);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_StartValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        assertEquals(forLoop.getStartValue(), startValue);
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_StepValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        assertEquals(forLoop.getStep(), stepValue);
    }


    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_NullCounterThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(null, endValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Counter cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValue_NullEndValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, null);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "EndValue cannot be null");
        }
    }


    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_NullCounterThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(null, endValue, stepValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Counter cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_NullEndValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, null, stepValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "EndValue cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValue_NullStepValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, endValue, null);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "StepValue cannot be null");
        }
    }


    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_NullCounterThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(null, startValue, endValue, stepValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Counter cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_NullStartValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, null, endValue, stepValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "StartValue cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_NullEndValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, endValue, null, stepValue);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "EndValue cannot be null");
        }
    }

    @Test
    public void TestForLoop_ConstructorWithCounterAndEndValueAndStepValueAndStartValue_NullStepValueThrowsException() {
        try {
            ForLoop forLoop = new ForLoop(counter, startValue, endValue, null);
            fail("Null type didn't result in exception");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "StepValue cannot be null");
        }
    }


    @Test
    public void TestForLoop_SetterAndGetterCounter_CounterIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        Variable newCounter = new Variable("i", "int");
        forLoop.setCounter(newCounter);
        assertEquals(forLoop.getCounter(), newCounter);
    }

    @Test
    public void TestForLoop_SetterAndGetterStartValue_StartValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        Variable newStartValue = new Variable("i", "int");
        forLoop.setStartValue(newStartValue);
        assertEquals(forLoop.getStartValue(), newStartValue);
    }

    @Test
    public void TestForLoop_SetterAndGetterEndValue_EndValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        Variable newEndValue = new Variable("i", "int");
        forLoop.setEndValue(newEndValue);
        assertEquals(forLoop.getEndValue(), newEndValue);
    }

    @Test
    public void TestForLoop_SetterAndGetterStepValue_StepValueIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        Variable newStepValue = new Variable("i", "int");
        forLoop.setStep(newStepValue);
        assertEquals(forLoop.getStep(), newStepValue);
    }

    @Test
    public void TestForLoop_SetterAndGetterContentHead_ContentHeadIsSet() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        forLoop.setHeadOfContent(head);
        assertEquals(forLoop.getHeadOfContent(), head);
    }


    @Test
    public void TestForLoop_ToCode_CodeIsFormatted() {
        ForLoop forLoop = new ForLoop(counter, startValue, endValue, stepValue);
        forLoop.setHeadOfContent(head);
        assertEquals(forLoop.toCode(2), "  for (i = 4; i < 40; i += 2) {\n" + head.getProgramCode(2 + Settings.getTabDepth()) + "\n  }");
    }
}
