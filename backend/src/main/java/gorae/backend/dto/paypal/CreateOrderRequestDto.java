package gorae.backend.dto.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.NonNull;

import java.util.List;

public record CreateOrderRequestDto(
        @JsonProperty("purchase_units") @NonNull List<PurchaseUnit> purchaseUnits,
        @NonNull PaypalOrderIntent intent) {

    public record PurchaseUnit(Amount amount) {
        public record Amount(@JsonProperty("currency_code") String currencyCode, String value) {
        }
    }

    public enum PaypalOrderIntent {
        CAPTURE,
        AUTHORIZE
    }
}
