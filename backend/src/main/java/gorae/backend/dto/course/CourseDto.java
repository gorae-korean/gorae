package gorae.backend.dto.course;

import gorae.backend.constant.TextbookLevel;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CourseDto(
        UUID id,
        String title,
        Instant startTime,
        Instant endTime,
        TextbookLevel level
) {
}
