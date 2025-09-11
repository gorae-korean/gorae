package gorae.backend.dto.auth;

import java.util.Set;

public record GoogleScopeRequestDto(
        Set<String> requiredScopes
) {
}
