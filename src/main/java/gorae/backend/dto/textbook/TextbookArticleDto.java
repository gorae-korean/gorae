package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "본문 조회 시 정보")
public record TextbookArticleDto(
        @Schema(description = "한글 제목", example = "한국에서 손 들어도 택시 안 잡히는 이유")
        String koreanTitle,

        @Schema(description = "한글 부제", example = "실제로 한국에서 택시 잡는 방법")
        String koreanSubtitle,

        @Schema(description = "본문", example = "<실제 본문>")
        String article
) {
}
