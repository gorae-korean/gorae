package gorae.backend.dto.member;

public record LoginRequestDto(
        String email,
        String password,
        AccountType accountType
) {
}
