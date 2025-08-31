package gorae.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

@Schema(description = "API 응답")
public record ResponseDto<T>(
        @Schema(description = "응답 상태", defaultValue = "SUCCESS")
        ResponseStatus status,

        @Schema(description = "HTTP 상태 코드")
        int httpCode,

        @Schema(description = "응답 데이터")
        T value,

        @Schema(description = "에러 데이터")
        ErrorDto error
) {
    public static ResponseDto<Void> createSuccess() {
        return new ResponseDto<>(ResponseStatus.SUCCESS, HttpStatus.OK.value(), null, null);
    }

    public static <V> ResponseDto<V> createSuccess(V value) {
        return new ResponseDto<>(ResponseStatus.SUCCESS, HttpStatus.OK.value(), value, null);
    }

    public static ResponseDto<Void> createError(HttpStatus status, String code, String message) {
        ErrorDto error = new ErrorDto(code, message);
        return new ResponseDto<>(ResponseStatus.ERROR, status.value(), null, error);
    }

    public record ErrorDto(
            @Schema(description = "에러 코드", example = "EXAMPLE_ERROR_CODE")
            String code,

            @Schema(description = "에러 메시지", example = "Error occurred.")
            String message) {
    }
}
