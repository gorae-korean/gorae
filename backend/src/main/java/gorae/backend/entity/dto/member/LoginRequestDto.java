package gorae.backend.entity.dto.member;

public record LoginRequestDto(
        String email,
        String password,
        AccountType accountType
) {
}
