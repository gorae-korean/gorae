package gorae.backend.controller;

import gorae.backend.entity.dto.ResponseDto;
import gorae.backend.entity.dto.ResponseStatus;
import gorae.backend.entity.dto.enrollment.EnrollRequestDto;
import gorae.backend.entity.dto.enrollment.EnrollmentDto;
import gorae.backend.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    public ResponseEntity<ResponseDto<EnrollmentDto>> enroll(
            Authentication authentication, EnrollRequestDto dto) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userDetails.getUsername();
        log.info("[API] Enroll requested: {}", userId);

        EnrollmentDto enrollmentDto = enrollmentService.enroll(userId, dto);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, enrollmentDto));
    }
}
