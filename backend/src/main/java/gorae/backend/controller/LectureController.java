package gorae.backend.controller;

import gorae.backend.common.google.GoogleHttpClient;
import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.google.SpaceDto;
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
@RequestMapping("/api/lectures")
public class LectureController {
    private final GoogleHttpClient googleHttpClient;

    @PostMapping
    public ResponseEntity<ResponseDto<SpaceDto>> createSpace(Authentication authentication) throws Exception {
        SpaceDto spaceDto = googleHttpClient.createSpace(authentication);
        return ResponseEntity.ok()
                .body(new ResponseDto<>(ResponseStatus.SUCCESS, spaceDto));
    }
}
