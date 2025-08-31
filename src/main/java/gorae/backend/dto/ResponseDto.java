package gorae.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 응답")
public record ResponseDto<T>(
        @Schema(description = "응답 상태", defaultValue = "SUCCESS")
        ResponseStatus status,

        @Schema(description = "응답 데이터")
        T value,

        @Schema(description = "에러 데이터")
        ErrorDto error
) {
    public static ResponseDto<Void> createSuccess() {
        return new ResponseDto<>(ResponseStatus.SUCCESS, null, null);
    }

    public static <V> ResponseDto<V> createSuccess(V value) {
        return new ResponseDto<>(ResponseStatus.SUCCESS, value, null);
    }

    public static ResponseDto<Void> createError(String code, String message) {
        ErrorDto error = new ErrorDto(code, message);
        return new ResponseDto<>(ResponseStatus.ERROR, null, error);
    }

    public record ErrorDto(
            @Schema(description = "에러 코드", example = "EXAMPLE_ERROR_CODE")
            String code,

            @Schema(description = "에러 메시지", example = "Error occurred.")
            String message) {
    }
}
