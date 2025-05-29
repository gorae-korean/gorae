package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.dto.client.paypal.CreateOrderDto;
import gorae.backend.service.CheckoutService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkouts")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateOrderDto>> checkout(
            Authentication authentication,
            @RequestBody CheckoutRequestDto dto
    ) throws Exception {
        String userId = getUserId(authentication);
        log.info("[API] Checkout requested: {}", userId);

        CreateOrderDto createOrderDto = checkoutService.checkout(userId, dto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, createOrderDto));
    }

    @GetMapping("/complete")
    public ResponseEntity<ResponseDto<String>> completeCheckout(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        log.info("[API] CheckoutSuccess requested, orderId: {}", orderId);
        checkoutService.completeCheckout(orderId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "구매가 정상적으로 완료되었습니다."));
    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseDto<String>> cancelCheckout(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        log.info("[API] CancelCheckout requested, orderId: {}", orderId);
        checkoutService.cancelCheckout(orderId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "주문이 취소되었습니다."));
    }
}
