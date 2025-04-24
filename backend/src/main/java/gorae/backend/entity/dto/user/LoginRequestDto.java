package gorae.backend.entity.dto.user;

public record LoginRequestDto(
        String email,
        String password,
        AccountType accountType
) {
}
