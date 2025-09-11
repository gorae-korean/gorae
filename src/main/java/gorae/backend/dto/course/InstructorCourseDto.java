package gorae.backend.dto.course;

import gorae.backend.constant.CourseStatus;
import gorae.backend.dto.member.StudentDto;
import gorae.backend.dto.textbook.TextbookSearchDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record InstructorCourseDto(
        @Schema(description = "강좌 ID")
        UUID id,

        @Schema(description = "강좌 교재")
        TextbookSearchDto textbook,

        @Schema(description = "학생 목록")
        List<StudentDto> students,

        @Schema(description = "강좌 시작 시간")
        Instant startTime,

        @Schema(description = "강좌 종료 시간")
        Instant endTime,

        @Schema(description = "수강 신청 인원")
        int currentCount,

        @Schema(description = "최대 수강 인원")
        int maxCount,

        @Schema(description = "강좌 상태")
        CourseStatus status
) {
}
