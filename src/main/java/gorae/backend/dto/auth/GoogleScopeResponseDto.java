package gorae.backend.dto.auth;

import java.util.UUID;

public record GoogleScopeResponseDto(
        String authorizationUrl,
        UUID state
) {
}
