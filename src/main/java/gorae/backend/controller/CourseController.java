package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.service.CourseService;
import io.swagger.v3.oas.annotations.Parameter;
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

    @CommonApiResponses(summary = "강좌 검색", description = "하나 이상의 매개변수를 사용해야 함")
    @ApiResponse(responseCode = "200", description = "강좌 검색 성공")
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseDto>>> searchCourses(
            Authentication authentication,
            @Parameter(description = "교재 ID") @RequestParam(required = false) UUID textbookId,
            @Parameter(description = "시작 시간") @RequestParam(required = false) ZonedDateTime startTime
    ) {

        log.info("[API] SearchCourses requested: {}", getSubject(authentication));

        if (textbookId == null && startTime == null) {
            throw new IllegalArgumentException("한 개 이상의 매개변수가 필요합니다.");
        }

        List<CourseDto> courses = courseService.searchCourses(textbookId, startTime);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, courses));
    }

    @CommonApiResponses(summary = "강좌가 있는 시간대 검색", description = "검색 날짜의 시간은 자정으로 고정해야 함")
    @ApiResponse(responseCode = "200", description = "시간대 검색 성공")
    @GetMapping("/timetable")
    public ResponseEntity<ResponseDto<List<OffsetTime>>> getTimetable(
            Authentication authentication,
            @Parameter(description = "검색 날짜 ") @RequestParam ZonedDateTime dateTime) {

        log.info("[API] GetTimetable requested: {}", getSubject(authentication));
        List<OffsetTime> timetable = courseService.getTimetable(dateTime);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, timetable));
    }
}
