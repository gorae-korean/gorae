package gorae.backend.dto.course;

import gorae.backend.constant.TextbookLevel;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CourseDto(
        String title,
        Instant startTime,
        Instant endTime,
        TextbookLevel level
) {
}
