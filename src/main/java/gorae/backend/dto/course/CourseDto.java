package gorae.backend.dto.course;

import gorae.backend.constant.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
@Schema(description = "강좌 정보")
public record CourseDto(
        @Schema(description = "강좌 ID")
        UUID id,

        @Schema(description = "시작 시간", example = "2025-07-01T09:00:00Z")
        Instant startTime,

        @Schema(description = "종료 시간", example = "2025-07-01T10:00:00Z")
        Instant endTime,

        @Schema(description = "교재 레벨")
        TextbookLevel level
) {
}
