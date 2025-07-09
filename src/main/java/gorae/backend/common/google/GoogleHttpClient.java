package gorae.backend.common.google;

import com.fasterxml.jackson.databind.ObjectMapper;
import gorae.backend.constant.endpoint.GoogleEndpoint;
import gorae.backend.dto.client.google.SpaceConfig;
import gorae.backend.dto.client.google.SpaceDto;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.service.OAuthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static gorae.backend.constant.endpoint.Endpoint.createUrl;

@Slf4j
@Component
public class GoogleHttpClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final GoogleProperties googleProperties;
    private final OAuthTokenService oAuthTokenService;

    private static final String BEARER = "Bearer ";

    @Autowired
    public GoogleHttpClient(ObjectMapper objectMapper, GoogleProperties googleProperties, OAuthTokenService oAuthTokenService) {
        this.objectMapper = objectMapper;
        this.googleProperties = googleProperties;
        this.oAuthTokenService = oAuthTokenService;
        httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
    }

    public SpaceDto createSpace(Instructor instructor) throws Exception {
        String accessToken = oAuthTokenService.getAccessToken(instructor);
        SpaceConfig spaceConfig = SpaceConfig.forLecture();
        SpaceDto spaceDto = SpaceDto.builder()
                .config(spaceConfig)
                .build();
        String payload = objectMapper.writeValueAsString(spaceDto);

        UUID instructorPublicId = instructor.getPublicId();
        log.info("[Google] CreateSpace started for member: {}", instructorPublicId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(createUrl(googleProperties.getMeetBaseUrl(), GoogleEndpoint.CREATE_SPACE)))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, BEARER + accessToken)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String content = response.body();
        log.debug(content);

        int statusCode = response.statusCode();
        if (statusCode == 200) {
            log.info("[Google] CreateSpace succeeded for member: {}", instructorPublicId);
        } else {
            log.warn("[Google] CreateSpace failed for member: {}, code: {}", instructorPublicId, statusCode);
        }

        return objectMapper.readValue(content, SpaceDto.class);
    }
}
