package gorae.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    // 400 BAD_REQUEST
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이메일이 이미 존재합니다."),
    WRONG_CREDENTIAL(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 잘못되었습니다."),

    // 404 NOT_FOUND
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
