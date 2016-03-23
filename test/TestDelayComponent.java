/**
 * Created by mathias on 23/03/16.
 */
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDelayComponent {
    @Test
    public void TestDelay_Microseconds_ReturnsInMicroseconds() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMicroseconds(423);

        assertEquals(delay.getDelayMicroseconds(), 423L);
    }

    @Test
    public void TestDelay_Milliseconds_ReturnsInMicroseconds() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMilliseconds(233);

        assertEquals(delay.getDelayMicroseconds(), 233000L);
    }

    @Test
    public void TestDelay_Seconds_ReturnsInMicroseconds() {
        DelayComponent delay = new DelayComponent();

        delay.setDelaySeconds(122);

        assertEquals(delay.getDelayMicroseconds(), 122000000L);
    }

    @Test
    public void TestDelay_MicroSecondsToCode_ReturnsMicroseconds() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMicroseconds(17);

        assertEquals(delay.toCode(0), "delayMicroseconds(17);");
    }

    @Test
    public void TestDelay_MicroSecondsToCode_ConvertsToMilliseconds() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMicroseconds(4000);

        assertEquals(delay.toCode(0), "delay(4);");
    }

    @Test
    public void TestDelay_MicroSecondsToCode_ConvertsToMixDelays() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMicroseconds(4050);

        assertEquals(delay.toCode(2), "  delay(4);\n  delayMicroseconds(50);");
    }

    @Test
    public void TestDelay_MicroSecondsToCode_ConvertsToMixDelaysInLoop() {
        DelayComponent delay = new DelayComponent();

        delay.setDelayMicroseconds(17179869182006L);

        assertEquals(delay.toCode(2), "  delay(4294967295);\n  delay(4294967295);\n  delay(4294967295);\n  delay(4294967295);\n  delay(2);\n  delayMicroseconds(6);");
    }

    @Test
    public void TestDelay_NegativeMicroSecondsToCode_ThrowsArgumentException() {
        DelayComponent delay = new DelayComponent();
        try {
            delay.setDelayMicroseconds(-1);
            fail("Delay can be negative number");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Delay must be greater than zero");
        }
    }

    @Test
    public void TestDelay_NegativeMilliSecondsToCode_ThrowsArgumentException() {
        DelayComponent delay = new DelayComponent();
        try {
            delay.setDelayMilliseconds(-1);
            fail("Delay can be negative number");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Delay must be greater than zero");
        }
    }

    @Test
    public void TestDelay_NegativeSecondsToCode_ThrowsArgumentException() {
        DelayComponent delay = new DelayComponent();
        try {
            delay.setDelaySeconds(-1);
            fail("Delay can be negative number");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Delay must be greater than zero");
        }
    }
}
