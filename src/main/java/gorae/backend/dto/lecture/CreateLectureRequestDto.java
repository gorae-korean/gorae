package gorae.backend.dto.lecture;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CreateLectureRequestDto(
        @Schema(description = "강좌 ID")
        UUID courseId
) {
}
