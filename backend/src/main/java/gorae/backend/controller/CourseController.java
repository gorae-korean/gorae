package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseDto>>> searchCourses(
            Authentication authentication,
            @RequestParam(required = false) UUID textbookId,
            @RequestParam(required = false) ZonedDateTime startTime
    ) {
        String userId = getUserId(authentication);
        log.info("[API] SearchCourses requested: {}", userId);

        if (textbookId == null && startTime == null) {
            throw new IllegalArgumentException("한 개 이상의 매개변수가 필요합니다.");
        }

        List<CourseDto> courses = courseService.searchCourses(textbookId, startTime);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, courses));
    }
}
