package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.instructor.AvailabilityAddRequestDto;
import gorae.backend.dto.instructor.AvailabilityDto;
import gorae.backend.service.InstructorService;
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

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/instructors")
@Tag(name = "강사 전용 API")
public class InstructorController {
    private final InstructorService instructorService;

    @CommonApiResponses(summary = "강사 일정 조회")
    @ApiResponse(
            responseCode = "200",
            description = "강사 일정 조회 성공"
    )
    @GetMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<List<AvailabilityDto>>> getAvailabilities(Authentication authentication) {
        String userId = getId(authentication);
        String subject = getSubject(authentication);
        log.info("[API] GetAvailabilities requested from sub: {}", subject);

        List<AvailabilityDto> availabilities = instructorService.getAvailabilities(userId);
        log.info("[API] GetAvailabilities responded to sub: {}", subject);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, availabilities));
    }

    @CommonApiResponses(summary = "강사 일정 추가")
    @ApiResponse(
            responseCode = "200",
            description = "강사 일정 추가 성공",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                        {
                                            "status": "SUCCESS",
                                            "data": "일정이 정상적으로 추가되었습니다."
                                        }
                                    """
                    )
            )
    )
    @PostMapping("/availabilities")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<String>> addAvailability(
            Authentication authentication,
            @RequestBody AvailabilityAddRequestDto dto
    ) {
        String userId = getId(authentication);
        String subject = getSubject(authentication);
        log.info("[API] AddAvailability requested from sub: {}", subject);

        instructorService.addAvailability(userId, dto);
        log.info("[API] AddAvailability responded to sub: {}", subject);
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
