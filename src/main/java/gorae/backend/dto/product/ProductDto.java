package gorae.backend.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Schema(description = "수강권 상품")
public record ProductDto(
        @Schema(description = "상품 ID")
        UUID id,

        @Schema(description = "상품 이름", example = "Trial Ticket")
        String name,

        @Schema(description = "수강권 개수", example = "1")
        int count,

        @Schema(description = "상품 가격", example = "10.00")
        BigDecimal price,

        @Schema(description = "할인된 가격", example = "8.00")
        BigDecimal discountedPrice,
        
        @Schema(description = "할인율", example = "10")
        BigDecimal discountPercentage,

        @Schema(description = "화폐 단위", example = "USD")
        String currencyCode,
        
        @Schema(description = "할인 여부", example = "true")
        boolean hasDiscount,

        @Schema(description = "장점", example = "[\"1:4 group lesson\"]")
        List<String> benefits
) {
}
