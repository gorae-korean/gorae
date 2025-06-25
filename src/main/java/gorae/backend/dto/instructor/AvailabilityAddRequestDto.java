package gorae.backend.dto.instructor;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Schema(description = "강사 일정 추가 요청 객체")
public record AvailabilityAddRequestDto(
        @Schema(description = "일정 시작 시간", example = "09:00:00")
        LocalTime startTime,

        @Schema(description = "일정 종료 시간", example = "18:00:00")
        LocalTime endTime,

        @Schema(description = "가능한 요일")
        DayOfWeek dayOfWeek
) {
}
