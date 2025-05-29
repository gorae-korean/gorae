package gorae.backend.dto.client.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseUnit(Amount amount) {
    public record Amount(@JsonProperty("currency_code") String currencyCode, String value) {
    }
}