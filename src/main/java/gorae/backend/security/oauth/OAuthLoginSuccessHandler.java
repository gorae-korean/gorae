package gorae.backend.security.oauth;

import gorae.backend.common.ProfileUtils;
import gorae.backend.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ProfileUtils profileUtils;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(oAuth2User);

        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                oauth2Token.getAuthorizedClientRegistrationId(),
                oauth2Token.getName()
        );

        log.debug("Access token: {}", client.getAccessToken().getTokenValue());
        if (client.getRefreshToken() != null) {
            log.debug("Refresh token: {}", client.getRefreshToken().getTokenValue());
        }

        Cookie cookie = new Cookie("auth_token", token);
        cookie.setMaxAge(86400 * 7);
        cookie.setSecure(profileUtils.isProdMode());
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        if (profileUtils.isProdMode()) {
            cookie.setDomain("goraekorean.site");
        }

        response.addCookie(cookie);
        response.sendRedirect(frontendUrl);
    }
}
