package gorae.backend.dto.textbook;

import gorae.backend.constant.textbook.TextbookCategory;
import gorae.backend.constant.textbook.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "교재 검색 요청 객체")
public record TextbookSearchRequestDto(
        @Schema(description = "검색할 제목의 단어", example = "taxi")
        String title,

        @Schema(description = "검색할 카테고리", example = "DAILY_LIFE")
        TextbookCategory category,

        @Schema(description = "검색할 레벨", example = "BASIC")
        TextbookLevel level
) {
}
