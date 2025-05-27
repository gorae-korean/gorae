package gorae.backend.common;

import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

public class JwtUtils {
    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUserId(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication cannot be null");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2User oAuth2User) {
            return Objects.requireNonNull(oAuth2User.getAttribute("id")).toString();
        } else if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        throw new CustomException(ErrorStatus.INVALID_USER_IDENTIFIER);
    }
}
