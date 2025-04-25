package gorae.backend.entity.dto.member;

public record SignupRequestDto(
        String email,
        String name,
        String password,
        String phoneNumber,
        AccountType accountType
) {
}
