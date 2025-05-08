package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.paypal.CreateOrderDto;
import gorae.backend.dto.paypal.CreateOrderRequestDto;
import gorae.backend.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static gorae.backend.common.JwtUtils.getUserId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<ResponseDto<CreateOrderDto>> checkout(
            Authentication authentication,
            @RequestBody CreateOrderRequestDto dto
    ) throws Exception {
        String userId = getUserId(authentication);
        log.info("[API] Checkout requested: {}", userId);

        CreateOrderDto createOrderDto = checkoutService.checkout(dto);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, createOrderDto));
    }
}
