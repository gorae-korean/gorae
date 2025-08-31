package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.service.LectureService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDto<LectureDto>> createLectureManually(Authentication authentication) throws Exception {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] CreateLectureManually requested from sub: {}", publicId);

        LectureDto lectureDto = lectureService.createLecture(userId);
        log.info("[API] CreateLectureManually responded to sub: {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess(lectureDto));
    }

    @CommonApiResponses(
            summary = "화상 강의 참여",
            description = """
                    강사가 실수로 나갔거나 학생들이 참여해야 하는 경우에 요청합니다.<br>\
                    학생이 요청했을 경우 학생들의 이메일은 표시되지 않습니다.
                    """
    )
    @ApiResponse(responseCode = "200", description = "화상 강의 참여 성공")
    @GetMapping("/join")
    public ResponseEntity<ResponseDto<LectureDto>> joinLecture(Authentication authentication) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] JoinLecture requested from sub: {}", publicId);

        LectureDto lectureDto = lectureService.joinLecture(userId);
        log.info("[API] JoinLecture responded to sub: {}", publicId);
        return ResponseEntity.ok()
                .body(ResponseDto.createSuccess(lectureDto));
    }
}
