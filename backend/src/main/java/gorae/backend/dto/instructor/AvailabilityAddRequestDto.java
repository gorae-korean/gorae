package gorae.backend.dto.instructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityAddRequestDto(
        LocalTime startTime,
        LocalTime endTime,
        DayOfWeek dayOfWeek
) {
}
