package gorae.backend.dto.product;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductSummaryDto(
        UUID id,
        String name,
        BigDecimal price,
        BigDecimal discountedPrice,
        BigDecimal discountAmount,
        BigDecimal discountPercentage,
        String currencyCode,
        boolean hasDiscount,
        int lessonRatio,
        int count
) {
}
