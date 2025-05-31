package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureController {
    private final LectureService lectureService;

    @PostMapping("/manual")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ResponseDto<LectureDto>> createLectureManually(Authentication authentication) throws Exception {
        String userId = getUserId(authentication);
        log.info("[API] CreateLectureManually requested: {}", userId);
        LectureDto lectureDto = lectureService.createLectureManually(userId);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, lectureDto));
    }

//    @GetMapping("/join")
//    public ResponseEntity<ResponseDto<LectureDto>> joinLecture(Authentication authentication) throws Exception {
//        String userId = getUserId(authentication);
//        log.info("[API] JoinLecture requested: {}", userId);
//    }
}
