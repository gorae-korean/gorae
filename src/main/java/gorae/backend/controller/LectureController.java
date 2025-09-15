package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.lecture.CreateLectureRequestDto;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.service.LectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getPublicId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
@Tag(name = "화상 강의 API")
public class LectureController {
    private final LectureService lectureService;

    @CommonApiResponses(summary = "화상 강의 생성", description = "강사만 이용 가능한 기능입니다.")
    @ApiResponse(responseCode = "200", description = "화상 강의 생성 성공")
    @PostMapping
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<UUID>> createLectureManually(
            Authentication authentication,
            @RequestBody CreateLectureRequestDto requestDto
    ) throws Exception {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] CreateLectureManually requested from {}", publicId);

        UUID lectureId = lectureService.createLecture(userId, requestDto);
        log.info("[API] CreateLectureManually responded to {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess(lectureId));
    }

    @CommonApiResponses(
            summary = "화상 강의 참여",
            description = """
                    학생들이 참여해야 하는 경우에 요청합니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "화상 강의 참여 성공")
    @GetMapping("/join")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<LectureDto>> joinLecture(
            Authentication authentication,
            @RequestParam UUID courseId
    ) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] JoinLecture requested from {}", publicId);

        LectureDto lectureDto = lectureService.joinLecture(userId, courseId);
        log.info("[API] JoinLecture responded to {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess(lectureDto));
    }

    @Operation(summary = "생성된 화상 강의 정보 조회")
    @ApiResponse(responseCode = "200", description = "화상 강의 정보 조회 성공")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<LectureDto>> getLectureInfo(
            Authentication authentication,
            @PathVariable(name = "id") UUID lectureId
    ) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] GetLectureInfo requested from {}, lecture: {}", publicId, lectureId);

        LectureDto lectureDto = lectureService.getLectureInfo(userId, lectureId);
        log.info("[API] GetLectureInfo responded to {}, lecture: {}", publicId, lectureId);
        return ResponseEntity.ok(ResponseDto.createSuccess(lectureDto));
    }

    @Operation(summary = "화상 강의 완료")
    @ApiResponse(responseCode = "200", description = "화상 강의 완료 성공")
    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<Void>> completeLecture(
            Authentication authentication,
            @PathVariable(name = "id") UUID lectureId
    ) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] CompleteLecture requested from {}, lecture: {}", publicId, lectureId);

        lectureService.completeLecture(userId, lectureId);
        log.info("[API] CompleteLecture responded to {}, lecture: {}", publicId, lectureId);
        return ResponseEntity.ok(ResponseDto.createSuccess());
    }
}
