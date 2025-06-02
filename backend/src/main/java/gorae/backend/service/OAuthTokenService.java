package gorae.backend.service;

import gorae.backend.entity.Member;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthTokenService {
    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;
    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    public String getAccessToken(Member member) {
        OAuth2AuthorizedClient client = auth2AuthorizedClientService.loadAuthorizedClient(
                member.getProvider(),
                member.getOauthId());

        if (client == null) {
            throw new CustomException(ErrorStatus.CLIENT_IS_NULL);
        }

        if (isAccessTokenExpired(client)) {
            client = refreshAccessToken(client, createAuthentication(member));
        }

        return client.getAccessToken().getTokenValue();
    }

    private OAuth2AuthenticationToken createAuthentication(Member member) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
        OAuth2User oAuth2User = new DefaultOAuth2User(
                authorities,
                Map.of("sub", member.getOauthId()),
                "sub"
        );

        return new OAuth2AuthenticationToken(
                oAuth2User,
                authorities,
                member.getProvider());
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

    private OAuth2AuthorizedClient refreshAccessToken(OAuth2AuthorizedClient client,
                                                      OAuth2AuthenticationToken authentication) {
        if (client.getRefreshToken() == null) {
            throw new CustomException(ErrorStatus.INVALID_REFRESH_TOKEN);
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        HttpServletResponse response = attributes.getResponse();

        OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId(client.getClientRegistration().getRegistrationId())
                .principal(authentication)
                .attribute(HttpServletRequest.class.getName(), request)
                .attribute(HttpServletResponse.class.getName(), response)
                .build();

        OAuth2AuthorizedClient refreshedClient = auth2AuthorizedClientManager.authorize(authorizeRequest);
        if (refreshedClient == null || refreshedClient.getAccessToken() == null) {
            throw new CustomException(ErrorStatus.FAILED_TO_GET_REFRESH_TOKEN);
        }

        auth2AuthorizedClientService.saveAuthorizedClient(refreshedClient, authentication);
        log.debug("Successfully created new access token");
        return refreshedClient;
    }
}
