package gorae.backend.dto.client.paypal;

import gorae.backend.constant.OrderStatus;

import java.util.List;

/**
 * <a href="https://developer.paypal.com/docs/api/orders/v2/#orders_create">
 * API 문서 바로가기
 * </a>
 */
public record CreateOrderDto(
        String id,
        OrderStatus status,
        List<Link> links
) {
    public record Link(String href, String rel, String method) {
    }
}
