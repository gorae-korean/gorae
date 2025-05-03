package gorae.backend.controller;

import gorae.backend.entity.dto.ResponseDto;
import gorae.backend.entity.dto.ResponseStatus;
import gorae.backend.entity.dto.course.CourseDto;
import gorae.backend.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseDto>>> searchCourses(
            Authentication authentication,
            @RequestParam(required = false) Long textbookId,
            @RequestParam(required = false) LocalDateTime startTime) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("SearchCourses requested: {}", userId);

        if (textbookId == null && startTime == null) {
            throw new IllegalArgumentException("한 개 이상의 매개변수가 필요합니다.");
        }

        List<CourseDto> courses = courseService.searchCourses(textbookId, startTime);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, courses));
    }
}
