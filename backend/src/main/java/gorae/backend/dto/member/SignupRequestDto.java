package gorae.backend.dto.member;

import gorae.backend.constant.AccountType;

public record SignupRequestDto(
        String email,
        String name,
        String password,
        String phoneNumber,
        AccountType accountType
) {
}
