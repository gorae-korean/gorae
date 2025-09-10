package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.dto.instructor.AvailabilityAddRequestDto;
import gorae.backend.dto.instructor.AvailabilityDto;
import gorae.backend.dto.instructor.InstructorDto;
import gorae.backend.service.InstructorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getPublicId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/instructors")
@Tag(name = "강사 전용 API")
public class InstructorController {
    private final InstructorService instructorService;

    @CommonApiResponses(summary = "강사 목록 조회")
    @ApiResponse(
            responseCode = "200",
            description = "강사 목록 조회 성공"
    )
    @GetMapping
    public ResponseEntity<ResponseDto<List<InstructorDto>>> getInstructors() {
        List<InstructorDto> instructors = instructorService.getInstructors();
        return ResponseEntity.ok(ResponseDto.createSuccess(instructors));
    }

    @CommonApiResponses(summary = "강사 일정 조회")
    @ApiResponse(
            responseCode = "200",
            description = "강사 일정 조회 성공"
    )
    @GetMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<List<AvailabilityDto>>> getAvailabilities(Authentication authentication) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] GetAvailabilities requested from {}", publicId);

        List<AvailabilityDto> availabilities = instructorService.getAvailabilities(userId);
        log.info("[API] GetAvailabilities responded to {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess(availabilities));
    }

    @CommonApiResponses(summary = "강사 일정 추가")
    @ApiResponse(
            responseCode = "200",
            description = "강사 일정 추가 성공"
    )
    @PostMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<Void>> addAvailability(
            Authentication authentication,
            @RequestBody AvailabilityAddRequestDto dto
    ) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] AddAvailability requested from {}", publicId);

        instructorService.addAvailability(userId, dto);
        log.info("[API] AddAvailability responded to {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess());
    }

    @CommonApiResponses(summary = "강사 강의 조회")
    @ApiResponse(responseCode = "200", description = "강사 강의 조회 성공")
    @GetMapping("/courses")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<List<CourseDto>>> getInstructorCourses(Authentication authentication) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] GetInstructorCourses requested from {}", publicId);

        List<CourseDto> courses = instructorService.getInstructorCourses(userId);
        log.info("[API] GetInstructorCourses responded to {}", publicId);
        return ResponseEntity.ok(ResponseDto.createSuccess(courses));
    }
}
