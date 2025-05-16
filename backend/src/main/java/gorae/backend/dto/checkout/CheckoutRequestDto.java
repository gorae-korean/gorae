package gorae.backend.dto.checkout;

import gorae.backend.constant.PaypalOrderIntent;

public record CheckoutRequestDto(
        Long productId,
        PaypalOrderIntent intent
) {
}
