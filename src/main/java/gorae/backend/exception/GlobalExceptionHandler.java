package gorae.backend.exception;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

import static gorae.backend.common.JwtUtils.getSubject;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private String getCurrentUserSubject() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            try {
                return getSubject(authentication);
            } catch (Exception e) {
                return "unknown";
            }
        }
        return "unauthenticated";
    }
    
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleCustomException(CustomException e) {
        String subject = getCurrentUserSubject();
        log.error("[Exception] CustomException for user: {}, error: {}, message: {}", 
                subject, e.getErrorStatus().name(), e.getMessage());
        
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(new ResponseDto<>(ResponseStatus.ERROR, Map.of(
                        "code", e.getErrorStatus().name(),
                        "message", e.getMessage()
                )));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDto<Map<String, String>>> handleAuthorizationDeniedException() {
        String subject = getCurrentUserSubject();
        log.error("[Exception] AuthorizationDeniedException for user: {}", subject);
        
        CustomException exception = new CustomException(ErrorStatus.NO_PERMISSIONS);
        return handleCustomException(exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<String>> handleRuntimeException(RuntimeException e) {
        String subject = getCurrentUserSubject();
        log.error("[Exception] RuntimeException for user: {}, message: {}", subject, e.getMessage(), e);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto<>(ResponseStatus.ERROR, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<String>> handleException(Exception e) throws NoResourceFoundException {
        if (e instanceof NoResourceFoundException e1) {
            throw e1;
        }

        String subject = getCurrentUserSubject();
        log.error("[Exception] Unhandled exception for user: {}, message: {}", subject, e.getMessage(), e);
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDto<>(ResponseStatus.ERROR, "서버에서 에러가 발생했습니다."));
    }
}