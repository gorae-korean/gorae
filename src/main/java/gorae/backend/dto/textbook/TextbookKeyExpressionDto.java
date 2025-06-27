package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "교재 핵심 표현 조회 시 정보")
public record TextbookKeyExpressionDto(
        @Schema(description = "한국어 표현", example = "__에 가요.")
        String koreanExpression,

        @Schema(description = "영어 표현", example = "Go to __")
        String englishExpression,

        @Schema(description = "예문 목록", example = "[\"인사동에 가요.\", \"서울역에 가요.\", \"내일 공항에 가요.\"]")
        List<String> examples,

        boolean isPublished,

        List<TextbookKeyExpressionActivityDto> activities
) {
}
