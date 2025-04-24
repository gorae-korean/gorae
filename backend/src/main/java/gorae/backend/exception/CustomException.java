package gorae.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorStatus errorStatus;

    @Override
    public String getMessage() {
        return errorStatus.getMessage();
    }
}
