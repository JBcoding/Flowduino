import static org.junit.Assert.*;

import org.junit.Test;

public class TestJUT {
    @Test
    public void testLogin() {
        JUT h = new JUT();
        assertFalse(h.gett());
    }
}