package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.instructor.AvailabilityAddRequestDto;
import gorae.backend.dto.instructor.AvailabilityDto;
import gorae.backend.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/instructors")
public class InstructorController {
    private final InstructorService instructorService;

    @GetMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<List<AvailabilityDto>>> getAvailabilities(Authentication authentication) {
        String userId = getUserId(authentication);
        log.info("[API] GetAvailabilities requested: {}", userId);
        List<AvailabilityDto> availabilities = instructorService.getAvailabilities(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, availabilities));
    }

    @PostMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<String>> addAvailability(
            Authentication authentication,
            @RequestBody AvailabilityAddRequestDto dto
    ) {
        String userId = getUserId(authentication);
        log.info("[API] AddAvailability requested: {}", userId);
        instructorService.addAvailability(userId, dto);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, "일정이 정상적으로 추가되었습니다."));
    }

//    @PostMapping("/unavailabilites")
//    @PreAuthorize("hasRole('INSTRUCTOR')")
//    public ResponseEntity<ResponseDto<String>> addUnavailability(
//            Authentication authentication,
//            @RequestBody
//    )
}
