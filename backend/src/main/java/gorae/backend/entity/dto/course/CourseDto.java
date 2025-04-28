package gorae.backend.entity.dto.course;

import gorae.backend.entity.constant.CourseLevel;
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
