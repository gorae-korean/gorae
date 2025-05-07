package gorae.backend.dto.course;

import gorae.backend.constant.CourseLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CourseDto(
        String title,
        LocalDateTime startTime,
        LocalDateTime endTime,
        CourseLevel level
) {
}
