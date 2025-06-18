package gorae.backend.dto.checkout;

import java.util.UUID;

public record CheckoutRequestDto(
        UUID productId
) {
}
