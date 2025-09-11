package gorae.backend.service.auth;

import gorae.backend.common.google.GoogleProperties;
import gorae.backend.dto.auth.GoogleScopeRequestDto;
import gorae.backend.dto.auth.GoogleScopeResponseDto;
import gorae.backend.dto.client.google.AccessTokenDto;
import gorae.backend.entity.Member;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {
    private final GoogleProperties googleProperties;
    private final MemberRepository memberRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final OAuth2AuthorizedClientRepository authorizedClientRepository;
    private final OAuthTokenService oAuthTokenService;
    private final RestTemplate restTemplate;

    private static final String GOOGLE = "google";

    @Value("${spring.baseurl}")
    private String baseUrl;

    public GoogleScopeResponseDto generateAuthUri(String publicId, GoogleScopeRequestDto requestDto) {
        log.info("[Service] GenerateAuthUri started from {}", publicId);
        UUID state = UUID.randomUUID();
        String scopes = String.join(" ", requestDto.requiredScopes());
        String redirectUri = baseUrl + googleProperties.getOauthCallbackEndpoint();

        String authorizationUrl = UriComponentsBuilder
                .fromUriString(googleProperties.getOauthBaseUrl())
                .queryParam("client_id", googleProperties.getClientId())
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", scopes)
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .queryParam("state", state)
                .build()
                .toUriString();

        log.info("[Service] GenerateAuthUri succeeded for member: {}, scopes: {}", publicId, state);
        return new GoogleScopeResponseDto(authorizationUrl, state);
    }

    // TODO: state 검증 추가
    @Transactional
    public void handleCallback(String userId, UUID state, String code, String scope) {
        long memberId = Long.parseLong(userId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        UUID publicId = member.getPublicId();
        log.info("[Service] HandleCallback started for {}, scope: {}", publicId, scope);

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(GOOGLE);
        if (clientRegistration == null) {
            log.warn("[Service] HandleCallback failed for {}: client registration not found for 'google'", publicId);
            throw new IllegalStateException("Client registration not found");
        }

        log.debug(clientRegistration.toString());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            log.warn("[Service] HandleCallback failed for {}: request context not available", publicId);
            throw new IllegalStateException("Request context not available");
        }

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        Authentication authentication = oAuthTokenService.createAuthentication(member);

        AccessTokenDto accessTokenDto = exchangeCodeForToken(code, clientRegistration);
        String accessTokenValue = accessTokenDto.accessToken();
        String refreshTokenValue = accessTokenDto.refreshToken();
        int expiresIn = accessTokenDto.expiresIn();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plusSeconds(expiresIn);

        OAuth2AuthorizedClient existingClient = authorizedClientRepository.loadAuthorizedClient(
                GOOGLE, authentication, request);
        Set<String> mergedScopes = new HashSet<>();

        if (!scope.isBlank()) {
            Arrays.stream(scope.split(" "))
                    .filter(s -> !s.isBlank())
                    .forEach(mergedScopes::add);
        }

        if (existingClient != null && existingClient.getAccessToken() != null) {
            Set<String> existingScopes = existingClient.getAccessToken().getScopes();
            if (existingScopes != null) {
                mergedScopes.addAll(existingScopes);
            }
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                accessTokenValue,
                issuedAt,
                expiresAt,
                mergedScopes
        );

        OAuth2RefreshToken refreshToken = null;
        if (refreshTokenValue != null) {
            int secondsToAdd = accessTokenDto.refreshTokenExpiresIn();
            refreshToken = new OAuth2RefreshToken(refreshTokenValue, issuedAt, issuedAt.plusSeconds(secondsToAdd));
        }

        OAuth2AuthorizedClient authorizedClient = new OAuth2AuthorizedClient(
                clientRegistration, member.getOauthId(), accessToken, refreshToken);

        authorizedClientRepository.saveAuthorizedClient(authorizedClient, authentication, request, response);
        log.info("[Service] HandleCallback succeeded for {}, scope: {}", publicId, scope);
    }

    private AccessTokenDto exchangeCodeForToken(String code, ClientRegistration clientRegistration) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("client_id", clientRegistration.getClientId());
        body.add("client_secret", clientRegistration.getClientSecret());
        body.add("redirect_uri", baseUrl + googleProperties.getOauthCallbackEndpoint());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<AccessTokenDto> response = restTemplate.postForEntity(
                clientRegistration.getProviderDetails().getTokenUri(),
                request,
                AccessTokenDto.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Token exchange failed with status: {}", response.getStatusCode());
            throw new IllegalStateException("Failed to exchange code for token");
        }

        return response.getBody();
    }

    public Set<String> getDefaultScopes() {
        return clientRegistrationRepository.findByRegistrationId(GOOGLE).getScopes();
    }
}
