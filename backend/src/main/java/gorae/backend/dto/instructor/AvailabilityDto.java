package gorae.backend.dto.instructor;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record AvailabilityDto(
        UUID id,
        LocalTime startTime,
        LocalTime endTime,
        DayOfWeek dayOfWeek,
        boolean isActive
) {
}
