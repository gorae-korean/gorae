package gorae.backend.common.google;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GoogleProperties {
    @Value("${google.meet.baseUrl}")
    private String meetBaseUrl;

    @Value("${google.oauth.baseUrl}")
    private String oauthBaseUrl;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
}
