package gorae.backend.service;

import gorae.backend.constant.CheckoutOrderStatus;
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

    @Transactional
    public void handleCheckoutOrderApproved(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        CheckoutOrderStatus orderStatus = order.getStatus();
        switch (orderStatus) {
            case PENDING -> {
                log.info("[Webhook] Processing approved order: {} (PENDING -> APPROVED)", orderId);
                checkoutCompletionService.handleApprovedOrder(order);
            }
            case APPROVED -> log.info("[Webhook] Order {} already approved - duplicate webhook ignored", orderId);
            case COMPLETED -> log.warn("[Webhook] Received approval webhook for already completed order: {}", orderId);
            default -> {
                log.error("[Webhook] Invalid state transition for order: {} ({} -> APPROVED)", orderId, orderStatus);
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
            case APPROVED -> {
                log.info("[Webhook] Processing payment completion: {} (APPROVED -> COMPLETED)", orderId);
                checkoutCompletionService.handleCompletedOrder(order);
            }
            case PENDING -> {
                log.info("[Webhook] Fast-track completion: {} (PENDING -> COMPLETED)", orderId);
                checkoutCompletionService.handleCompletedOrder(order);
            }
            case COMPLETED -> log.info("[Webhook] Order {} already completed - duplicate webhook ignored", orderId);
            default -> {
                log.error("[Webhook] Invalid state transition for order: {} ({} -> COMPLETED)", orderId, orderStatus);
                throw new CustomException(ErrorStatus.INVALID_ORDER_STATUS);
            }
        }
    }
}
