package fx.chart.util.time;

import fx.chart.util.Helper;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;


public class TimeFormatTest {

    @Test
    void testTimeFormats() {
        System.out.println("\n-------------------- times test --------------------");
        final LocalTime time = LocalTime.of(12, 03, 00);

        assert "12:03:00.0000".equals(fx.chart.util.time.Times.HH_mm_ss_SSSS.format(time));
        assert "12:03".equals(fx.chart.util.time.Times.HH_mm.format(time));
        assert "120300".equals(fx.chart.util.time.Times.HHmmss.format(time));
        assert "120300.0000".equals(Times.HHmmss_SSSS.format(time));
    }

    @Test
    void testSecondsToFormats() {
        System.out.println("\n------------------- seconds test -------------------");
        final long seconds1 = 2_167_592;

        assert "25:02:06".equals(Helper.secondsToDDHHMMString(seconds1));
        assert "25:02:06:32".equals(Helper.secondsToDDHHMMSSString(seconds1));
        assert "02:06".equals(Helper.secondsToHHMMString(seconds1));
        assert "02:06:32".equals(Helper.secondsToHHMMSSString(seconds1));
    }
}
