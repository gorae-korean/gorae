package gorae.backend.dto.client.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import gorae.backend.constant.ProductName;
import gorae.backend.entity.Product;

import java.util.List;

public record PurchaseUnit(List<Item> items, Amount amount) {
    public static PurchaseUnit createPurchaseUnit(Product product) {
        String currencyCode = product.getCurrencyCode();
        String value = String.valueOf(product.getDiscountedPrice());
        Amount unitAmount = new Amount(currencyCode, value, null);

        ProductName productName = product.getName();
        String name = productName.getKey() + " Voucher";
        String description;
        if (productName == ProductName.FIRST_CLASS) {
            description = "First Class - valid until 10/09";
        } else {
            description = String.format("%s - %d days validity", productName.getKey(), product.getDuration());
        }

        return new PurchaseUnit(
                List.of(new Item(name, description, 1, unitAmount)),
                Amount.createTotalAmount(currencyCode, value)
        );
    }

    public record Item(String name, String description, int quantity, @JsonProperty("unit_amount") Amount unitAmount) {
    }

    public record Amount(@JsonProperty("currency_code") String currencyCode, String value, Breakdown breakdown) {
        public static Amount createTotalAmount(String currencyCode, String value) {
            return new Amount(currencyCode, value, new Breakdown(new Amount(currencyCode, value, null)));
        }

        public record Breakdown(@JsonProperty("item_total") Amount total) {
        }
    }
}