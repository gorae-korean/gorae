package gorae.backend.controller;

import gorae.backend.entity.dto.ResponseDto;
import gorae.backend.entity.dto.enrollment.EnrollRequestDto;
import gorae.backend.entity.dto.enrollment.EnrollmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/enrollments")
public class EnrollmentController {
    @PostMapping("/enroll")
    public ResponseEntity<ResponseDto<EnrollmentDto>> enroll(
            Authentication authentication, EnrollRequestDto dto) {
        // TODO: 수강 신청 기능 구현
        return null;
    }
}
