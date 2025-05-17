package gorae.backend.dto.textbook;

import java.util.Set;

public record TextbookSearchRequestDto(
        String word,
        Set<String> tags
) {
}
