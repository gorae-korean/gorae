package gorae.backend.controller;

import gorae.backend.dto.client.paypal.PaypalWebhookDto;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.service.PaypalWebhookService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/webhooks/paypal")
@RequiredArgsConstructor
public class PaypalWebhookController {
    private final PaypalWebhookService paypalWebhookService;

    @Hidden
    @PostMapping
    ResponseEntity<Void> handleWebhook(@RequestBody PaypalWebhookDto webhookData) {
        log.info("[Webhook] New Paypal webhook received for id: {}, summary: {}", webhookData.id(), webhookData.summary());
        log.debug(webhookData.toString());

        String orderId;
        switch (webhookData.eventType()) {
            case "CHECKOUT.ORDER.APPROVED" -> {
                orderId = webhookData.resource().get("id").toString();
                paypalWebhookService.handleCheckoutOrderApproved(orderId);
            }
            case "PAYMENT.CAPTURE.COMPLETED" -> {
                orderId = getOrderIdFromCapture(webhookData.resource());
                paypalWebhookService.handlePaymentCaptureCompleted(orderId);
            }
            default -> throw new CustomException(ErrorStatus.UNDEFINED_WEBHOOK_EVENT);
        }

        return ResponseEntity.ok().build();
    }

    @SuppressWarnings("unchecked")
    private static String getOrderIdFromCapture(Map<String, Object> resource) {
        Map<String, Object> supplementaryData = (Map<String, Object>) resource.get("supplementary_data");
        if (supplementaryData != null) {
            Map<String, Object> relatedIds = (Map<String, Object>) supplementaryData.get("related_ids");
            if (relatedIds != null && relatedIds.containsKey("order_id")) {
                return relatedIds.get("order_id").toString();
            }
        }
        throw new CustomException(ErrorStatus.INVALID_ORDER_ID_PATH);
    }
}
