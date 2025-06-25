package gorae.backend.dto.textbook;

import gorae.backend.constant.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "교재 검색 요청 객체")
public record TextbookSearchRequestDto(
        @Schema(description = "검색할 단어", example = "설날")
        String word,

        @Schema(description = "검색할 태그", example = "[\"제이팍\"]")
        Set<String> tags,

        @Schema(description = "검색할 레벨")
        TextbookLevel level
) {
}
