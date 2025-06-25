package gorae.backend.dto.enrollment;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
@Schema(description = "수강 신청 내역")
public record EnrollmentDto(
        @Schema(description = "수강 신청 내역 ID")
        UUID id,

        @Schema(description = "수강 신청 시간")
        Instant enrolledAt,

        @Schema(description = "수강 신청 과목")
        CourseDto course,

        @Schema(description = "수강 신청 상태")
        EnrollmentStatus status
) {
}
