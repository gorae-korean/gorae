package gorae.backend.dto.textbook;

import gorae.backend.entity.textbook.key_expression.ImageAndText;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "교재 핵심 표현에서의 활동")
public record TextbookKeyExpressionActivityDto(
        @Schema(
                description = "활동 질문",
                example = "아래의 장소 그림을 보고 '___에 가요.'를 활용하여 문장을 완성해보세요."
        )
        String question,

        List<ImageAndText> examples
) {
}
