package gorae.backend.dto.textbook;

import lombok.Builder;

import java.util.Set;

@Builder
public record TextbookDto(
        Long id,
        String title,
        Set<String> tags
) {
}
