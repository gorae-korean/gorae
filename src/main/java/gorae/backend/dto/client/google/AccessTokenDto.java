package gorae.backend.dto.client.google;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") int expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("refresh_token_expires_in") int refreshTokenExpiresIn,
        String scope,
        @JsonProperty("token_type") String tokenType
) {
}
