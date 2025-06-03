package gorae.backend.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

@Slf4j
public class JwtUtils {
    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUserId(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication cannot be null");
        }

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        return Objects.requireNonNull(oauthUser.getAttribute("id")).toString();
    }
}
