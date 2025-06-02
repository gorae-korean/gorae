package gorae.backend.dto.enrollment;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import lombok.Builder;

import java.time.Instant;

@Builder
public record EnrollmentDto(
        Long id,
        Instant enrolledAt,
        CourseDto course,
        EnrollmentStatus status
) {
}
