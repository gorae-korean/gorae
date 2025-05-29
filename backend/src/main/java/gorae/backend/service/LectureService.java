package gorae.backend.service;

import gorae.backend.common.google.GoogleHttpClient;
import gorae.backend.dto.client.google.SpaceDto;
import gorae.backend.dto.lecture.LectureDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LectureService {
    private final GoogleHttpClient googleHttpClient;

    public LectureDto createLecture(Authentication authentication) throws Exception {
        SpaceDto spaceDto = googleHttpClient.createSpace(authentication);
        return new LectureDto(spaceDto.meetingCode(), spaceDto.meetingUri());
    }
}
