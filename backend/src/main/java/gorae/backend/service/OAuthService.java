package gorae.backend.service;

import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    public String getAccessToken(Authentication authentication) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
        String name = oauthToken.getName();
        OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(
                authorizedClientRegistrationId,
                name);

        if (isAccessTokenExpired(client)) {
            client = refreshAccessToken(client, oauthToken);
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

    private OAuth2AuthorizedClient refreshAccessToken(OAuth2AuthorizedClient client, OAuth2AuthenticationToken authenticationToken) {
        if (client.getRefreshToken() == null) {
            throw new CustomException(ErrorStatus.INVALID_REFRESH_TOKEN);
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        HttpServletResponse response = attributes.getResponse();

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(client.getClientRegistration().getRegistrationId())
                .principal(authenticationToken)
                .attribute(HttpServletRequest.class.getName(), request)
                .attribute(HttpServletResponse.class.getName(), response)
                .build();

        OAuth2AuthorizedClient refreshedClient = auth2AuthorizedClientManager.authorize(authorizeRequest);
        if (refreshedClient == null || refreshedClient.getAccessToken() == null) {
            throw new CustomException(ErrorStatus.FAILED_TO_GET_REFRESH_TOKEN);
        }

        auth2AuthorizedClientService.saveAuthorizedClient(refreshedClient, authenticationToken);
        return refreshedClient;
    }
}
