package gorae.backend.controller;

import gorae.backend.common.JwtUtils;
import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.checkout.ApproveRequestDto;
import gorae.backend.dto.checkout.CheckoutOrderDto;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.service.checkout.CheckoutService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getPublicId;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/checkouts")
@Tag(name = "결제 API")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @CommonApiResponses(summary = "결제 링크 생성", description = "반환된 링크를 프론트엔드에서 리다이렉트 시켜야 합니다.")
    @ApiResponse(
            responseCode = "200",
            description = "결제 요청 성공",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "status": "SUCCESS",
                                        "value": "https://www.paypal.com/checkoutnow?token=5O190127TN364715T"
                                    }
                                    """
                    )
            )
    )
    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<String>> checkout(
            Authentication authentication,
            @RequestBody CheckoutRequestDto dto
    ) throws Exception {
        if (dto.productId() == null) {
            throw new CustomException(ErrorStatus.INVALID_PARAMETER);
        }

        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] Checkout requested from {}", publicId);

        String redirectUrl = checkoutService.checkout(userId, dto);
        log.info("[API] Checkout responded to {}", publicId);
        return ResponseEntity.ok(ResponseDto.createSuccess(redirectUrl));
    }

    @Hidden
    @GetMapping("/cancel")
    public ResponseEntity<ResponseDto<Void>> cancelCheckout(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        if (orderId == null || orderId.isBlank()) {
            throw new CustomException(ErrorStatus.INVALID_PARAMETER);
        }

        log.info("[API] CancelCheckout requested from order: {}", orderId);
        checkoutService.cancelCheckout(orderId);
        log.info("[API] CancelCheckout responded to order: {}", orderId);
        return ResponseEntity.ok(ResponseDto.createSuccess());
    }

    @CommonApiResponses(summary = "결제 검증 및 완료")
    @ApiResponse(responseCode = "200", description = "결제 검증 및 완료 성공")
    @PostMapping("/approve")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<Void>> approveCheckout(
            Authentication authentication, @RequestBody ApproveRequestDto dto) throws Exception {
        String orderId = dto.orderId();
        if (orderId == null || orderId.isBlank()) {
            throw new CustomException(ErrorStatus.INVALID_PARAMETER);
        }

        String userId = getId(authentication);
        String publicId = JwtUtils.getPublicId(authentication);
        log.info("[API] ApproveCheckout requested from user: {}, order: {}", publicId, orderId);
        checkoutService.approveCheckout(userId, orderId);

        log.info("[API] ApproveCheckout responded to user: {}, order: {}", publicId, orderId);
        return ResponseEntity.ok(ResponseDto.createSuccess());
    }

    @Operation(summary = "결제 내역 조회")
    @ApiResponse(responseCode = "200", description = "결제 내역 조회 성공")
    @GetMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ResponseDto<List<CheckoutOrderDto>>> getOrders(Authentication authentication) {
        String userId = getId(authentication);
        String publicId = getPublicId(authentication);
        log.info("[API] GetOrders requested from {}", publicId);

        List<CheckoutOrderDto> orderDtoList = checkoutService.getOrders(userId);
        log.info("[API] GetOrders responded to {}", publicId);
        return ResponseEntity.ok(ResponseDto.createSuccess(orderDtoList));
    }
}
