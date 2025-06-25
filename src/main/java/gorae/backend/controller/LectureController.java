package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.service.LectureService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static gorae.backend.common.JwtUtils.getSubject;
import static gorae.backend.common.JwtUtils.getId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/lectures")
@Tag(name = "화상 강의 API")
public class LectureController {
    private final LectureService lectureService;

    @CommonApiResponses(summary = "화상 강의 생성", description = "강사만 이용 가능")
    @ApiResponse(responseCode = "200", description = "화상 강의 생성 성공")
    @PostMapping("/manual")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<LectureDto>> createLectureManually(Authentication authentication) throws Exception {
        String userId = getId(authentication);
        log.info("[API] CreateLectureManually requested: {}", getSubject(authentication));
        LectureDto lectureDto = lectureService.createLectureManually(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, lectureDto));
    }

    @CommonApiResponses(
            summary = "화상 강의 참여",
            description = """
                    강사가 실수로 나갔거나 학생들이 참여해야 하는 경우에 요청함<br>\
                    학생이 요청했을 경우 학생들의 이메일은 표시되지 않음
                    """
    )
    @ApiResponse(responseCode = "200", description = "화상 강의 참여 성공")
    @GetMapping("/join")
    public ResponseEntity<ResponseDto<LectureDto>> joinLecture(Authentication authentication) {
        String userId = getId(authentication);
        log.info("[API] JoinLecture requested: {}", getSubject(authentication));
        LectureDto lectureDto = lectureService.joinLecture(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, lectureDto));
    }
}
