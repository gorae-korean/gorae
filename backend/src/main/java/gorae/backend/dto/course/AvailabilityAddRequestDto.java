package gorae.backend.dto.course;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;

public record AvailabilityAddRequestDto(
        @JsonProperty("start_hour") int startHour,
        @JsonProperty("end_hour") int endHour,
        @JsonProperty("day_of_week") DayOfWeek dayOfWeek
) {
}
