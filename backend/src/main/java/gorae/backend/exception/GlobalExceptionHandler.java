package gorae.backend.exception;

import gorae.backend.entity.dto.ResponseDto;
import gorae.backend.entity.dto.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<String>> handleCustomException(CustomException e) {
        log.error("CustomException occurred: {}", e.getErrorStatus().toString());
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new ResponseDto<>(ResponseStatus.ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException() {
        log.error("Exception occurred");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(ResponseStatus.ERROR, "서버에서 에러가 발생했습니다."));
    }
}
