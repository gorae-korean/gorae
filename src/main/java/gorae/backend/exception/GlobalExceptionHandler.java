package gorae.backend.exception;

import gorae.backend.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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
    public ResponseEntity<ResponseDto<Void>> handleCustomException(CustomException e) {
        String subject = getCurrentUserSubject();
        log.error("[Exception] CustomException for user: {}, code: {}, message: {}",
                subject, e.getErrorStatus().name(), e.getMessage());

        ErrorStatus errorStatus = e.getErrorStatus();
        return ResponseEntity
                .status(e.getErrorStatus().getStatus())
                .body(ResponseDto.createError(errorStatus.name(), errorStatus.getMessage()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ResponseDto<Void>> handleAuthorizationDeniedException() {
        String subject = getCurrentUserSubject();
        log.error("[Exception] AuthorizationDeniedException for user: {}", subject);
        
        CustomException exception = new CustomException(ErrorStatus.NO_PERMISSIONS);
        return handleCustomException(exception);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDto<Void>> handleRuntimeException(RuntimeException e) {
        String subject = getCurrentUserSubject();
        log.error("[Exception] RuntimeException for user: {}, message: {}", subject, e.getMessage(), e);
        
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.createError("RUNTIME_ERROR", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleException(Exception e) throws NoResourceFoundException {
        if (e instanceof NoResourceFoundException e1) {
            throw e1;
        }

        String subject = getCurrentUserSubject();
        log.error("[Exception] Unhandled exception for user: {}, message: {}", subject, e.getMessage(), e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDto.createError("SERVER_ERROR", "Error occurred in the server."));
    }
}