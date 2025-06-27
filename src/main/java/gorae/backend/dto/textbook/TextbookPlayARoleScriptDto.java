package gorae.backend.dto.textbook;

import gorae.backend.entity.textbook.play_a_role.Character;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "교재 역할놀이 대화 예시")
public record TextbookPlayARoleScriptDto(
        @Schema(description = "대화 순서", example = "1")
        int sequence,

        @Schema(description = "한국어 대화", example = "새해 복 많이 받으세요~ 이게 떡국인가요?")
        String koreanLine,

        @Schema(description = "영어 대화")
        String englishLine,

        Character speaker
) {
}
