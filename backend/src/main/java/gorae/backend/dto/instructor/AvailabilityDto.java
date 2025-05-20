package gorae.backend.dto.instructor;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Builder
public record AvailabilityDto(
        LocalTime startTime,
        LocalTime endTime,
        DayOfWeek dayOfWeek,
        boolean isActive
) {
}
