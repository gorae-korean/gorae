package gorae.backend.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {
    private TimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean timeOverlaps(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        // 경계 시간은 겹치지 않는 것으로 간주함
        return end1.isAfter(start2) && end2.isAfter(start1);
    }

    public static boolean isDateBetween(LocalDate cur, LocalDate start, LocalDate end) {
        // 경계 날짜는 겹치는 것으로 간주함
        return !cur.isBefore(start) && !cur.isAfter(end);
    }

    public static Instant getCurrentHour() {
        return Instant.now().truncatedTo(ChronoUnit.HOURS);
    }

    public static Instant getNextHour() {
        return getCurrentHour().plus(1, ChronoUnit.HOURS);
    }
}
