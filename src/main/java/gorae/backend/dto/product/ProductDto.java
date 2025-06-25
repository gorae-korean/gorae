package gorae.backend.dto.product;

import gorae.backend.constant.ProductName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Schema(description = "수강권 상품")
public record ProductDto(
        @Schema(description = "상품 ID")
        UUID id,

        @Schema(description = "상품 이름", example = "FIRST_TICKET")
        ProductName name,

        @Schema(description = "수강권 개수", example = "1")
        int count,

        @Schema(description = "상품 가격", example = "3000")
        BigDecimal price,

        @Schema(description = "화폐 단위", example = "KRW")
        String currencyCode
) {
}
