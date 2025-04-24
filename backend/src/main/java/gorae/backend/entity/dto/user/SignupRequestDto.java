package gorae.backend.entity.dto.user;

public record SignupRequestDto(
        String email,
        String name,
        String password,
        String phoneNumber,
        AccountType accountType
) {
}
