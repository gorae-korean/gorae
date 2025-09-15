package gorae.backend.dto.lecture;

import com.fasterxml.jackson.annotation.JsonInclude;
import gorae.backend.dto.member.StudentDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "화상 강의")
public record LectureDto(
        @Schema(description = "화상 강의 ID")
        UUID id,

        @Schema(description = "화상 강의 참여 코드", example = "eaa-ebzk-zdp")
        String code,

        @Schema(description = "화상 강의 참여 링크", example = "https://meet.google.com/eaa-ebzk-zdp")
        String redirectUrl,

        List<StudentDto> students
) {
}
