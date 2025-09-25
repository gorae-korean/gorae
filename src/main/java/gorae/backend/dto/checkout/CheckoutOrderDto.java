package gorae.backend.dto.checkout;

import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.dto.product.ProductSummaryDto;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CheckoutOrderDto(
        UUID id,
        CheckoutOrderStatus status,
        ProductSummaryDto product,
        int currentCount,
        Instant paymentDate,
        Instant endDate
) {
}
