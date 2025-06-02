package gorae.backend.dto.lecture;

import lombok.Builder;

import java.util.List;

@Builder
public record LectureDto(
        String code,
        String redirectUrl,
        List<String> studentEmails
) {
}
