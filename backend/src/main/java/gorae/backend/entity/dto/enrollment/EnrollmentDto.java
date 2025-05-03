package gorae.backend.entity.dto.enrollment;

import gorae.backend.entity.constant.EnrollmentStatus;
import gorae.backend.entity.dto.course.CourseDto;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EnrollmentDto(
        Long id,
        LocalDateTime enrolledAt,
        CourseDto course,
        EnrollmentStatus status
        ) {
}
