package gorae.backend.dto.paypal;

import java.util.List;

public record CreateOrderDto(
        String id,
        OrderStatus status,
        List<Link> links
) {
    public record Link(String href, String rel, String method) {
    }

    public enum OrderStatus {
        CREATED,
        SAVED,
        APPROVED,
        VOIDED,
        COMPLETED,
        PAYER_ACTION_REQUIRED
    }
}
