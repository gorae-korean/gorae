package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.enrollment.EnrollRequestDto;
import gorae.backend.dto.enrollment.EnrollmentDto;
import gorae.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ResponseDto<EnrollmentDto>> enroll(
            Authentication authentication, @RequestBody EnrollRequestDto dto) {

        String userId = getUserId(authentication);
        log.info("[API] Enroll requested: {}", userId);

        EnrollmentDto enrollmentDto = enrollmentService.enroll(userId, dto);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, enrollmentDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<EnrollmentDto>>> getEnrollments(Authentication authentication) {
        String userId = getUserId(authentication);
        log.info("[API] GetEnrollments requested: {}", userId);

        List<EnrollmentDto> enrollmentDtoList = enrollmentService.getEnrollments(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, enrollmentDtoList));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> drop(Authentication authentication, Long enrollmentId) {

        String userId = getUserId(authentication);
        log.info("[API] Drop requested: {}", userId);

        enrollmentService.drop(userId, enrollmentId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, null));
    }
}
