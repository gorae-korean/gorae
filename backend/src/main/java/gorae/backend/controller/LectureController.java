package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.service.LectureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PostMapping
    public ResponseEntity<ResponseDto<LectureDto>> createLecture(Authentication authentication) throws Exception {
        String userId = getUserId(authentication);
        log.info("[API] CreateLecture requested: {}", userId);
        LectureDto lecture = lectureService.createLecture(authentication);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, lecture));
    }
}
