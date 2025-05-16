package gorae.backend.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import gorae.backend.constant.PaypalOrderIntent;
import lombok.Builder;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * <a href="https://developer.paypal.com/docs/api/orders/v2/#orders_create">
 * API 문서 바로가기
 * </a>
 */
@Builder
public record CreateOrderRequestDto(
        @JsonProperty("purchase_units") @NonNull List<PurchaseUnit> purchaseUnits,
        @NonNull PaypalOrderIntent intent,
        @JsonProperty("payment_source") PaymentSource paymentSource
) {
}
