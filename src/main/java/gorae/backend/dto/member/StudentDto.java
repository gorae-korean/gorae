package gorae.backend.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;

public record StudentDto(
        @Schema(description = "학생 이름")
        String name,

        @Schema(description = "학생 프로필 사진 URL")
        String imageUrl
) {
}
