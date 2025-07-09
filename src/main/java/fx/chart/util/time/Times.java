package fx.chart.util.time;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public enum Times {

    /**
     * <p>Format : HH:mm:ss.SSSS</p>
     * <p>Example: 23:59:59.9999</p>
     */
    HH_mm_ss_SSSS("HH:mm:ss.SSSS"),

    /**
     * <p>Format : HH:mm:ss</p>
     * <p>Example: 23:59:59</p>
     */
    HH_mm_ss("HH:mm:ss"),

    /**
     * <p>Format : HH:mm</p>
     * <p>Example: 23:59</p>
     */
    HH_mm("HH:mm"),

    /**
     * <p>Format : HHmmss.SSSS</p>
     * <p>Example: 235959.9999</p>
     */
    HHmmss_SSSS("HHmmss.SSSS"),

    /**
     * <p>Format : HHmmss</p>
     * <p>Example: 235959</p>
     */
    HHmmss("HHmmss"),

    /**
     * <p>Format : HHmm</p>
     * <p>Example: 2359</p>
     */
    HHmm("HHmm"),

    /**
     * <p>Format : HH</p>
     * <p>Example: 23</p>
     */
    HH("HH");

    private final DateTimeFormatter formatter;


    Times(final String formatString) {
        formatter = DateTimeFormatter.ofPattern(formatString);
    }


    public LocalTime parse(final String text) {return LocalTime.parse(text, formatter);}

    public Instant parseTime(final String text) {return parseTime(text, ZoneId.systemDefault());}

    public Instant parseTime(final String text, final ZoneId zoneId) {return Instant.parse(text).atZone(zoneId).toInstant();}


    public String format(final LocalTime time) {return formatter.format(time);}

    public String format(final Instant time) {return format(time, ZoneId.systemDefault());}

    public String format(final Instant time, final ZoneId zoneId) {
        return format(time.atZone(zoneId).toLocalTime());
    }
}
