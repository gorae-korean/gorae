package gorae.backend.dto.textbook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

@Builder
public record TextbookDto(
        Long id,
        String title,
        Set<String> tags,
        @JsonProperty("instructor_name") String instructorName
) {
}
