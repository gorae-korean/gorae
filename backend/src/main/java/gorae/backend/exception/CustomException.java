package gorae.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorStatus status;

    @Override
    public String getMessage() {
        return status.getMessage();
    }
}
