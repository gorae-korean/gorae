package gorae.backend.dto.enrollment;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
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
