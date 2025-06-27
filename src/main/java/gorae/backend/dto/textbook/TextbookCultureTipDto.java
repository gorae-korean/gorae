package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "교재 문화 팁 조회 시 정보")
public record TextbookCultureTipDto(
        @Schema(description = "순서", example = "1")
        int sequence,

        @Schema(description = "제목", example = "한국 택시는 '아빠사자'만 알면 돼!")
        String title,

        @Schema(description = "부제")
        String subtitle,

        @Schema(description = "사진 URL", example = "https://example.com/static/image.png")
        String imageUrl,

        @Schema(description = "본문", example = "<실제 본문>")
        String text
) {
}
