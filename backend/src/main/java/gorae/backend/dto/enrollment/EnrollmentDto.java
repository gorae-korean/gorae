package gorae.backend.dto.enrollment;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record EnrollmentDto(
        UUID id,
        Instant enrolledAt,
        CourseDto course,
        EnrollmentStatus status
) {
}
