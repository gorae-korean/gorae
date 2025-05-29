package gorae.backend.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaypalAccessTokenDto(
        String scope,
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("app_id") String appId,
        @JsonProperty("expires_in") Long expiresIn,
        String nonce
) {
}
