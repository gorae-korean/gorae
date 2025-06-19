package gorae.backend.dto.lecture;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LectureDto(
        UUID id,
        String code,
        String redirectUrl,
        List<String> studentEmails
) {
}
