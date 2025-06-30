package gorae.backend.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "유저 기본 정보")
public record MyInfoDto(
        @Schema(description = "유저 이름", example = "김민형")
        String name,

        @Schema(description = "유저 이메일", example = "example@google.com")
        String email,

        @Schema(description = "유저 프로필 이미지 URL", example = "https://example.com/static/image.png")
        String imageUrl,

        @Schema(description = "유저의 역할 (\"ROLE_\" + \"STUDENT\"와 같은 형식으로 나타남)", example = "ROLE_STUDENT")
        String role
) {
}
