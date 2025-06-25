package gorae.backend.dto.checkout;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "결제 요청 객체")
public record CheckoutRequestDto(
        @Schema(description = "상품 ID")
        UUID productId
) {
}
