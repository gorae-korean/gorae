package gorae.backend.dto.textbook;

import gorae.backend.constant.textbook.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@Schema(description = "수업 교재")
public record TextbookDto(
        @Schema(description = "수업 교재 ID")
        UUID id,

        @Schema(description = "수업 교재 제목", example = "기초 한국어")
        String title,

        @Schema(description = "수업 교재 태그 목록", example = "[\"BTS\", \"봉준호\", \"손흥민\", \"제이팍\"]")
        Set<String> tags,

        @Schema(description = "수업 교재 레벨")
        TextbookLevel level
) {
}
