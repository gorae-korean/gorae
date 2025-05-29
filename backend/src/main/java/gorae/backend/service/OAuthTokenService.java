package gorae.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthTokenService {
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    public String getAccessToken(Authentication authentication) throws AuthenticationException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(
                oauthToken.getAuthorizedClientRegistrationId(),
                oauthToken.getName());

        if (isAccessTokenExpired(client)) {
            // TODO: 구현 필요
            throw new AuthenticationException("액세스 토큰 만료");
        }

        return client.getAccessToken().getTokenValue();
    }

    private boolean isAccessTokenExpired(OAuth2AuthorizedClient client) {
        if (client == null || client.getAccessToken() == null) {
            return true;
        }

        Instant expiresAt = client.getAccessToken().getExpiresAt();
        if (expiresAt == null) {
            return true;
        }

        return expiresAt.isBefore(Instant.now());
    }
}
