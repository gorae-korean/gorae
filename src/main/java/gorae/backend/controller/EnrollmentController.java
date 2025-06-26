package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.enrollment.EnrollRequestDto;
import gorae.backend.dto.enrollment.EnrollmentDto;
import gorae.backend.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollments")
@Tag(name = "수강 신청 API")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @CommonApiResponses(summary = "수강 신청")
    @ApiResponse(
            responseCode = "200",
            description = "수강 신청 성공"
    )
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<EnrollmentDto>> enroll(
            Authentication authentication, @RequestBody EnrollRequestDto dto) {

        String userId = getId(authentication);
        log.info("[API] Enroll requested: {}", getSubject(authentication));

        EnrollmentDto enrollmentDto = enrollmentService.enroll(userId, dto);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, enrollmentDto));
    }

    @CommonApiResponses(summary = "수강 신청 내역 조회")
    @ApiResponse(
            responseCode = "200",
            description = "수강 신청 내역 조회 성공"
    )
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<List<EnrollmentDto>>> getEnrollments(Authentication authentication) {
        String userId = getId(authentication);
        log.info("[API] GetEnrollments requested: {}", getSubject(authentication));
        log.debug("Role: {}", authentication.getAuthorities());

        List<EnrollmentDto> enrollmentDtoList = enrollmentService.getEnrollments(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, enrollmentDtoList));
    }

    @CommonApiResponses(summary = "수강 신청 취소")
    @ApiResponse(
            responseCode = "200",
            description = "수강 신청 취소 성공",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "status": "SUCCESS",
                                        "data": "정상적으로 취소되었습니다."
                                    }
                                    """
                    )
            )
    )
    @DeleteMapping("/{enrollmentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<String>> drop(
            Authentication authentication,
            @Parameter(description = "수강 신청 내역 ID", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID enrollmentId) {
        String userId = getId(authentication);
        log.info("[API] Drop requested: {}", getSubject(authentication));

        enrollmentService.drop(userId, enrollmentId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, "정상적으로 취소되었습니다."));
    }
}
