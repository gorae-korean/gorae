package gorae.backend.dto.textbook;

import gorae.backend.constant.textbook.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;

@Builder
@Schema(description = "본문 조회 시 정보")
public record TextbookArticleDto(
        @Schema(description = "한글 제목", example = "한국에서 손 들어도 택시 안 잡히는 이유")
        String title,

        @Schema(description = "한글 부제", example = "실제로 한국에서 택시 잡는 방법")
        String subtitle,

        @Schema(description = "본문", example = "<실제 본문>")
        String article,

        @Schema(description = "교재 태그", example = "[\"Transportation\"]")
        Set<String> tags,

        @Schema(description = "교재 레벨", example = "BASIC")
        TextbookLevel level,

        @Schema(description = "읽기 시간", example = "3")
        int readTime
) {
}
