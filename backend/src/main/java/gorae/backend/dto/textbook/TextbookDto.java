package gorae.backend.dto.textbook;

import gorae.backend.constant.TextbookLevel;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record TextbookDto(
        UUID id,
        String title,
        Set<String> tags,
        TextbookLevel level
) {
}
