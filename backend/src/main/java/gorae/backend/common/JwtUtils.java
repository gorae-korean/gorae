package gorae.backend.common;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtils {
    private JwtUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUserId(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalStateException("Authentication cannot be null");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
