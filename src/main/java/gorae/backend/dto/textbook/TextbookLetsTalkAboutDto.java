package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "교재 스몰 토크 정보")
public record TextbookLetsTalkAboutDto(
        @Schema(
                description = "스몰 토크 주제",
                example = "새해가 되어 친구들이 오랜만에 만나 새해 인사도 하고 서로의 근황을 나눕니다. " +
                        "명절에 무엇을 했는지, 새해 계획은 무엇인지 이야기합니다."
        )
        String title,

        @Schema(description = "사진 URL", example = "https://example.com/static/image.png")
        String imageUrl,

        @Schema(description = "스몰 토크 주요 포인트")
        List<String> discussionPoints
) {
}
