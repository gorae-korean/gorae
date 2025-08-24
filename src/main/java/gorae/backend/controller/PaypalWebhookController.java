package gorae.backend.controller;

import gorae.backend.dto.client.paypal.PaypalWebhookDto;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.service.PaypalWebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/webhooks/paypal")
@RequiredArgsConstructor
public class PaypalWebhookController {
    private final PaypalWebhookService paypalWebhookService;

    @PostMapping
    ResponseEntity<Void> handleWebhook(@RequestBody PaypalWebhookDto webhookData) {
        log.info("[Webhook] New Paypal webhook received for id: {}, summary: {}", webhookData.id(), webhookData.summary());
        String eventType = webhookData.eventType();
        log.debug("[Webhook] EventType: {}", eventType);

        String orderId = webhookData.resource().get("id").toString();
        if (orderId == null || orderId.isBlank()) {
            throw new CustomException(ErrorStatus.INVALID_ORDER_ID);
        }

        switch (eventType) {
            case "CHECKOUT.ORDER.APPROVED" -> paypalWebhookService.handleCheckoutOrderApproved(orderId);
            case "PAYMENT.CAPTURE.COMPLETED" -> paypalWebhookService.handlePaymentCaptureCompleted(orderId);
            default -> throw new CustomException(ErrorStatus.UNDEFINED_WEBHOOK_EVENT);
        }

        return ResponseEntity.ok().build();
    }
}
