package gorae.backend.dto.textbook;

import gorae.backend.entity.textbook.play_a_role.Character;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "교재 역할놀이 정보")
public record TextbookPlayARoleSceneDto(
        @Schema(description = "회화 주제", example = "새해 첫날 아침, 떡국을 먹는 상황")
        String title,

        @Schema(description = "사진 URL", example = "https://example.com/static/image.png")
        String imageUrl,

        List<Character> characters,

        List<TextbookPlayARoleScriptDto> scripts
) {
}
