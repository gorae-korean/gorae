package gorae.backend.dto.textbook;

import gorae.backend.constant.TextbookLevel;

import java.util.Set;

public record TextbookSearchRequestDto(
        String word,
        Set<String> tags,
        TextbookLevel level
) {
}
