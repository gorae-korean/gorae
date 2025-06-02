package gorae.backend.common;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    private TimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean timeOverlaps(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return end1.isAfter(start2) && end2.isAfter(start1);
    }

    public static Instant getCurrentHour() {
        return Instant.now().truncatedTo(ChronoUnit.HOURS);
    }

    public static Instant getNextHour() {
        return getCurrentHour().plus(1, ChronoUnit.HOURS);
    }
}
