package fx.chart.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 22 Jul 2025, 10:28 AM
 */
class HelperTest {

    @Test
    void calcNiceNumber() {
        assertEquals(2000.0, Helper.calcNiceNumber(1230, false), 1E-10);
        assertEquals(2000.0, Helper.calcNiceNumber(1999, false), 1E-10);
        assertEquals(2000.0, Helper.calcNiceNumber(2000, false), 1E-10);
        assertEquals(5000.0, Helper.calcNiceNumber(2001, false), 1E-10);
        assertEquals(5000.0, Helper.calcNiceNumber(5000, false), 1E-10);
        assertEquals(10000.0, Helper.calcNiceNumber(5001, false), 1E-10);

        double range = 0.15;
        int count = 10;
        System.out.println(Helper.calcNiceNumber(range/(count-1), true));
    }
}