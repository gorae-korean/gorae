package gorae.backend.dto.enrollment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "수강 신청 요청 객체")
public record EnrollRequestDto(
        @Schema(description = "강좌 ID")
        UUID courseId
) {
}
