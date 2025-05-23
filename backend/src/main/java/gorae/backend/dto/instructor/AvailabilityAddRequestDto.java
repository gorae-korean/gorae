package gorae.backend.dto.instructor;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record AvailabilityAddRequestDto(
        @JsonProperty("start_time") LocalTime startTime,
        @JsonProperty("end_time") LocalTime endTime,
        @JsonProperty("day_of_week") DayOfWeek dayOfWeek
) {
}
