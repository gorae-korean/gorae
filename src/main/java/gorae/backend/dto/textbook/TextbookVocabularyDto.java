package gorae.backend.dto.textbook;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "교재 핵심 단어 조회 시 정보")
public record TextbookVocabularyDto(
        @Schema(description = "한국어 단어", example = "운전하다")
        String koreanVocabulary,

        @Schema(description = "단어 발음", example = "Unjeonhada")
        String pronunciation,

        @Schema(description = "영어 단어", example = "To drive")
        String englishVocabulary,

        @Schema(description = "한국어 예문 1", example = "천천히 운전하다.")
        String koreanExampleSentence1,

        @Schema(description = "영어 예문 1", example = "Drive slowly.")
        String englishExampleSentence1,

        @Schema(description = "한국어 예문 2", example = "자동차를 운전하다.")
        String koreanExampleSentence2,

        @Schema(description = "영어 예문 2", example = "Drive a car.")
        String englishExampleSentence2
) {
}
