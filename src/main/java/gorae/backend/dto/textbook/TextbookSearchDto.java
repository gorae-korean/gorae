package gorae.backend.dto.textbook;

import gorae.backend.constant.textbook.TextbookLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
@Schema(description = "검색 시 수업 교재 정보")
public record TextbookSearchDto(
        @Schema(description = "수업 교재 ID")
        UUID id,

        @Schema(description = "수업 교재 제목", example = "Why Taxis Won't Stop in Korea")
        String title,

        @Schema(description = "수업 교재 부제", example = "How Korean taxis actually work")
        String subtitle,

        @Schema(description = "썸네일 주소", example = "https://example.com/static/thumbnail.png")
        String thumbnailUrl,

        @Schema(description = "읽기 시간", example = "3")
        int readTime,

        @Schema(description = "최신 교재 여부", example = "true")
        boolean isNew,

        @Schema(description = "수업 교재 태그 목록", example = "[\"Transportation\"]")
        Set<String> tags,

        @Schema(description = "수업 교재 레벨")
        TextbookLevel level
) {
}
