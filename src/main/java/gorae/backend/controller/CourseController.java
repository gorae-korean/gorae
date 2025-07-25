package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.service.CourseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
@Tag(name = "강좌 API")
public class CourseController {
    private final CourseService courseService;

    @CommonApiResponses(summary = "강좌 검색", description = "하나 이상의 매개변수를 사용해야 합니다.")
    @ApiResponse(responseCode = "200", description = "강좌 검색 성공")
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseDto>>> searchCourses(
            Authentication authentication,
            @Parameter(description = "교재 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @RequestParam(required = false) UUID textbookId,
            @Parameter(description = "시작 시간", example = "2025-01-01T13:00:00+09:00")
            @RequestParam(required = false) ZonedDateTime startTime
    ) {

        String subject = getSubject(authentication);
        log.info("[API] SearchCourses requested from sub: {}", subject);

        if (textbookId == null && startTime == null) {
            throw new IllegalArgumentException("한 개 이상의 매개변수가 필요합니다.");
        }

        List<CourseDto> courses = courseService.searchCourses(textbookId, startTime);
        log.info("[API] SearchCourses responded to sub: {}", subject);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, courses));
    }

    @CommonApiResponses(
            summary = "강좌가 있는 시간대 검색",
            description = "검색 날짜의 시간은 자정으로 고정해야 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "시간대 검색 성공",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "status": "SUCCESS",
                                        "data": [
                                            "12:00+09:00",
                                            "15:00+09:00",
                                            "16:00+09:00"
                                        ]
                                    }
                                    """
                    )
            )
    )
    @GetMapping("/timetable")
    public ResponseEntity<ResponseDto<List<OffsetTime>>> getTimetable(
            Authentication authentication,
            @Parameter(description = "검색 날짜 (시간은 자정으로 고정 필요)", example = "2025-01-01T00:00:00+09:00")
            @RequestParam ZonedDateTime dateTime) {

        String subject = getSubject(authentication);
        log.info("[API] GetTimetable requested from sub: {}", subject);
        List<OffsetTime> timetable = courseService.getTimetable(dateTime);
        log.info("[API] GetTimeTable responded to sub: {}", subject);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, timetable));
    }
}
