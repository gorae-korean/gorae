package gorae.backend.exception;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<String>> handleCustomException(CustomException e) {
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new ResponseDto<>(ResponseStatus.ERROR, e.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDto<String>> handleAuthorizationDeniedException() {
        CustomException exception = new CustomException(ErrorStatus.NO_PERMISSIONS);
        return ResponseEntity
                .status(exception.getErrorStatus().getStatus())
                .body(new ResponseDto<>(ResponseStatus.ERROR, exception.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto<>(ResponseStatus.ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException() {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(ResponseStatus.ERROR, "서버에서 에러가 발생했습니다."));
    }
}
