package fx.chart.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Utilities for data time
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 07 Jul 2025, 12:23 PM
 */
public final class TimeUtils {

    /**
     * Convert seconds to date-time at system {@link ZoneId}
     *
     * @param seconds the number of seconds from the epoch of 1970-01-01T00:00:00Z
     * @return {@link LocalDateTime} instance
     */
    public static LocalDateTime toDateTime(final long seconds) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), ZoneId.systemDefault());
    }

    /**
     * Converts seconds to date-time at given {@link ZoneId}
     *
     * @param seconds the number of seconds from the epoch of 1970-01-01T00:00:00Z
     * @param zoneId  {@link ZoneId}
     * @return {@link LocalDateTime} instance
     */
    public static LocalDateTime toDateTime(final long seconds, final ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(seconds), zoneId);
    }

    /**
     * Converts this date-time at system zoneid to the number of seconds from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param dateTime {@link LocalDateTime}
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    public static long toSeconds(final LocalDateTime dateTime) {
        return toSeconds(dateTime, ZoneId.systemDefault());
    }

    /**
     * Converts this date-time to the number of seconds from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param dateTime {@link LocalDateTime}
     * @param zoneId   {@link ZoneId}
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    public static long toSeconds(final LocalDateTime dateTime, final ZoneId zoneId) {
        return toSeconds(dateTime, TimeUtils.getZoneOffset(zoneId));
    }

    /**
     * Converts this date-time to the number of seconds from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param dateTime   {@link LocalDateTime}
     * @param zoneOffset {@link ZoneOffset}
     * @return the number of seconds from the epoch of 1970-01-01T00:00:00Z
     */
    public static long toSeconds(final LocalDateTime dateTime, final ZoneOffset zoneOffset) {
        return dateTime.toEpochSecond(zoneOffset);
    }

    /**
     * Converts this date-time to the number of milliseconds from the epoch of 1970-01-01T00:00:00Z.
     *
     * @param dateTime   {@link LocalDateTime}
     * @param zoneOffset {@link ZoneOffset}
     * @return the number of milliseconds from the epoch of 1970-01-01T00:00:00Z
     */
    public static long toMillis(final LocalDateTime dateTime, final ZoneOffset zoneOffset) {
        return dateTime.toEpochSecond(zoneOffset) * 1000;
    }

    /**
     * Return the current {@link ZoneOffset} of given {@link ZoneId}
     *
     * @param zoneId {@link ZoneId}
     * @return {@link ZoneOffset}
     */
    public static ZoneOffset getZoneOffset(final ZoneId zoneId) {
        return zoneId.getRules().getOffset(Instant.now());
    }

    /**
     * Return the current {@link ZoneOffset} of the system {@link ZoneId}
     *
     * @return {@link ZoneOffset}
     */
    public static ZoneOffset getZoneOffset() {
        return getZoneOffset(ZoneId.systemDefault());
    }

}
