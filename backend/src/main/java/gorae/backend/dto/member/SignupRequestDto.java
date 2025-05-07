package gorae.backend.dto.member;

public record SignupRequestDto(
        String email,
        String name,
        String password,
        String phoneNumber,
        AccountType accountType
) {
}
