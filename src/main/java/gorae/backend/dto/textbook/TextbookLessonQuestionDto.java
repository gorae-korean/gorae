package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "교재 질문 목록 조회 시 정보")
public record TextbookLessonQuestionDto(
        @Schema(description = "질문 번호", example = "1")
        int sequence,

        @Schema(description = "한국어 질문", example = "최근에 언제, 어디에서 택시를 탔나요?")
        String koreanQuestion,

        @Schema(description = "영어 질문", example = "When and where did you last take a taxi?")
        String englishQuestion
) {
}
