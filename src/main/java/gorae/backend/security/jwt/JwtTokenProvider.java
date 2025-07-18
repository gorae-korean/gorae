package gorae.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenProvider {
    private static final String ROLES ="roles";
    private static final String EMAIL = "email";
    private static final String ID = "id";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(OAuth2User oAuth2User) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        List<String> roles = oAuth2User.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        log.debug("OAuth2User attributes: {}", oAuth2User.getAttributes().toString());

        return Jwts.builder()
                .subject(oAuth2User.getName())
                .claim(ROLES, roles)
                .claim(EMAIL, oAuth2User.getAttribute(EMAIL))
                .claim(ID, oAuth2User.getAttribute(ID))
                .claim("picture", oAuth2User.getAttribute("picture"))
                .claim("name", oAuth2User.getAttribute("name"))
                .claim("registrationId", oAuth2User.getAttribute("registrationId"))
                .claim("nameAttributeKey", oAuth2User.getAttribute("nameAttributeKey"))
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserRoles(String token) {
        Claims claims = extractClaims(token);
        return claims.get(ROLES, List.class);
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
