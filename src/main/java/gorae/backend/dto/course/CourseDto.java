package gorae.backend.dto.course;

import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.dto.instructor.InstructorDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "강좌 정보")
public record CourseDto(
        @Schema(description = "강좌 ID")
        UUID id,

        @Schema(description = "강사 이름")
        InstructorDto instructor,

        @Schema(description = "교재 레벨")
        TextbookLevel level,

        @Schema(description = "최대 수업 인원", example = "4")
        int maxCount
) {
}
