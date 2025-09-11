package gorae.backend.service;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.constant.paypal.PaypalOrderStatus;
import gorae.backend.dto.client.paypal.OrderDto;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CheckoutOrderRepository;
import gorae.backend.service.checkout.CheckoutCompletionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaypalWebhookService {
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final CheckoutCompletionService checkoutCompletionService;
    private final PaypalHttpClient paypalHttpClient;

    @Transactional
    public void handleCheckoutOrderApproved(String orderId) throws Exception {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        CheckoutOrderStatus orderStatus = order.getStatus();
        switch (orderStatus) {
            case PENDING -> {
                log.info("[Webhook] Processing approved order: {} (PENDING -> APPROVED)", orderId);
                OrderDto orderDto = paypalHttpClient.captureOrder(order.getStudent().getPublicId(), orderId);
                if (orderDto.status() != PaypalOrderStatus.COMPLETED) {
                    checkoutCompletionService.handleFailedOrder(order);
                    throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
                }
                checkoutCompletionService.handleCompletedOrder(order);
            }
            case COMPLETED -> log.warn("[Webhook] Received approval webhook for already completed order: {}", orderId);
            default -> {
                log.warn("[Webhook] Invalid state transition for order: {} ({} -> APPROVED)", orderId, orderStatus);
                throw new CustomException(ErrorStatus.INVALID_ORDER_STATUS);
            }
        }
    }

    @Transactional
    public void handlePaymentCaptureCompleted(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        CheckoutOrderStatus orderStatus = order.getStatus();
        switch (orderStatus) {
            case PENDING -> {
                log.info("[Webhook] Processing payment completion: {} (PENDING -> COMPLETED)", orderId);
                checkoutCompletionService.handleCompletedOrder(order);
            }
            case COMPLETED -> log.info("[Webhook] Order {} already completed - duplicate webhook ignored", orderId);
            default -> {
                log.warn("[Webhook] Invalid state transition for order: {} ({} -> COMPLETED)", orderId, orderStatus);
                throw new CustomException(ErrorStatus.INVALID_ORDER_STATUS);
            }
        }
    }
}
