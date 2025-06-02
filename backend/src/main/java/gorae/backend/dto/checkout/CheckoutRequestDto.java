package gorae.backend.dto.checkout;

import gorae.backend.constant.PaypalOrderIntent;

import java.util.UUID;

public record CheckoutRequestDto(
        UUID productId,
        PaypalOrderIntent intent
) {
}
