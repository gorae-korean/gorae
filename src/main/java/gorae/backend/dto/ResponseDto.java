package gorae.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "API 응답")
public record ResponseDto<T>(
        @Schema(description = "응답 상태", defaultValue = "SUCCESS")
        ResponseStatus status,

        @Schema(description = "응답 데이터")
        T value
) {
}
