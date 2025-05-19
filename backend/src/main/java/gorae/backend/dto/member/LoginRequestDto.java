package gorae.backend.dto.member;

import gorae.backend.constant.AccountType;

public record LoginRequestDto(
        String email,
        String password,
        AccountType accountType
) {
}
