package gorae.backend.controller;

import gorae.backend.dto.ResponseDto;
import gorae.backend.dto.ResponseStatus;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.service.CheckoutService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static gorae.backend.common.JwtUtils.getId;
import static gorae.backend.common.JwtUtils.getSubject;

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
                                        "data": "https://www.paypal.com/checkoutnow?token=5O190127TN364715T"
                                    }
                                    """
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "결제 요청 중 문제 발생",
            content = @Content(
                    examples = {
                            @ExampleObject(
                                    name = "상품 첫 구매 불가",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "data": {
                                                    "message": "첫 구매 혜택이 이미 사용되었습니다.",
                                                    "code": "CANNOT_BUY_THE_FIRST_PRODUCT"
                                                }
                                            }
                                            """
                            ),
                            @ExampleObject(
                                    name = "리다이렉션 링크 오류",
                                    value = """
                                            {
                                                "status": "ERROR",
                                                "data": {
                                                    "message": "리다이렉션 링크를 찾을 수 없습니다.",
                                                    "code": "CANNOT_FIND_REDIRECTION_LINK"
                                                }
                                            }
                                            """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "필요한 데이터를 찾을 수 없음",
            content = @Content(
                    examples = @ExampleObject(
                            value = """
                                    {
                                        "status": "ERROR",
                                        "data": {
                                            "message": "상품이 존재하지 않습니다.",
                                            "code": "PRODUCT_NOT_FOUND"
                                        }
                                    }
                                    """
                    )
            )
    )
    @PostMapping
    public ResponseEntity<ResponseDto<String>> checkout(
            Authentication authentication,
            @RequestBody CheckoutRequestDto dto
    ) throws Exception {
        String userId = getId(authentication);
        String subject = getSubject(authentication);
        log.info("[API] Checkout requested from sub: {}", subject);

        String redirectUrl = checkoutService.checkout(userId, dto);
        log.info("[API] Checkout responded to sub: {}", subject);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, redirectUrl));
    }

    @Hidden
    @GetMapping("/complete")
    public ResponseEntity<ResponseDto<String>> completeCheckout(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        log.info("[API] CompleteCheckout requested from orderId: {}", orderId);
        checkoutService.completeCheckout(orderId);
        log.info("[API] CompleteCheckout responded to orderId: {}", orderId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "구매가 정상적으로 완료되었습니다."));
    }

    @Hidden
    @GetMapping("/cancel")
    public ResponseEntity<ResponseDto<String>> cancelCheckout(HttpServletRequest request) {
        String orderId = request.getParameter("token");
        log.info("[API] CancelCheckout requested from orderId: {}", orderId);
        checkoutService.cancelCheckout(orderId);
        log.info("[API] CancelCheckout responded to orderId: {}", orderId);
        return ResponseEntity.ok(new ResponseDto<>(ResponseStatus.SUCCESS, "주문이 취소되었습니다."));
    }
}
