package fx.chart.util;

import java.time.*;

/**
 * Utilities for data time
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 07 Jul 2025, 12:23 PM
 */
public final class TimeUtils {

    public static Instant clamp(final Instant value, final Instant min, final Instant max) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }

    public static LocalDateTime clamp(final LocalDateTime value, final LocalDateTime min, final LocalDateTime max) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }

    public static LocalDate clamp(final LocalDate value, final LocalDate min, final LocalDate max) {
        if (value.isBefore(min)) return min;
        if (value.isAfter(max)) return max;
        return value;
    }

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
     * convert seconds to HH:MM:SS format
     *
     * @param seconds seconds
     * @return time in HH:MM:SS format
     */
    public static String secondsToHHMMString(final long seconds) {
        long[] hhmmss = TimeUtils.secondsToHHMMSS(seconds);
        return String.format("%02d:%02d:%02d", hhmmss[0], hhmmss[1], hhmmss[2]);
    }

    /**
     * convert seconds to HHMMSS value
     *
     * @param seconds seconds
     * @return long array with hh mm ss
     */
    public static long[] secondsToHHMMSS(final long seconds) {
        long secs = seconds % 60;
        long minutes = (seconds / 60) % 60;
        long hours = (seconds / (60 * 60)) % 24;
        return new long[]{hours, minutes, secs};
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
