package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "교재 회화 조회 시 정보")
public record TextbookRealTalkDto(
        List<TextbookPlayARoleSceneDto> playARoles,

        List<TextbookLetsTalkAboutDto> letsTalkAbouts
) {
}
