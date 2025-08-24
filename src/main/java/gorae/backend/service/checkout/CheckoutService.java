package gorae.backend.service.checkout;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.constant.OrderStatus;
import gorae.backend.constant.PaypalOrderIntent;
import gorae.backend.constant.ProductName;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.dto.client.paypal.CreateOrderRequestDto;
import gorae.backend.dto.client.paypal.OrderDto;
import gorae.backend.dto.client.paypal.PaymentSource;
import gorae.backend.dto.client.paypal.PurchaseUnit;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Product;
import gorae.backend.entity.Student;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import gorae.backend.repository.CheckoutOrderRepository;
import gorae.backend.repository.ProductRepository;
import gorae.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {
    @Value("${frontend.url}")
    private String frontendUrl;

    @Value("${spring.baseurl}")
    private String baseUrl;

    private final PaypalHttpClient paypalHttpClient;
    private final CheckoutCompletionService checkoutCompletionService;
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final StudentRepository studentRepository;
    private final ProductRepository productRepository;

    private static final String PAYER_ACTION_REL = "payer-action";

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public String checkout(String userId, CheckoutRequestDto dto) throws Exception {
        Product product = productRepository.findByPublicId(dto.productId())
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));
        Student student = studentRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));

        UUID studentPublicId = student.getPublicId();
        log.info("[Service] Checkout started for member: {}", studentPublicId);

        if (!student.isFirst() && product.getName() == ProductName.FIRST_CLASS) {
            throw new CustomException(ErrorStatus.CANNOT_BUY_THE_FIRST_PRODUCT);
        }

        Optional<CheckoutOrder> optionalExistingOrder = checkoutOrderRepository
                .findByStudentAndProductAndStatus(student, product, OrderStatus.PAYER_ACTION_REQUIRED);
        if (optionalExistingOrder.isPresent()) {
            CheckoutOrder existingOrder = optionalExistingOrder.get();
            OrderDto existingOrderDto = paypalHttpClient.getOrder(studentPublicId, existingOrder.getOrderId());
            OrderStatus actualStatus = existingOrderDto.status();

            switch (actualStatus) {
                case PAYER_ACTION_REQUIRED -> {
                    return getPayNowUrl(existingOrderDto);
                }
                case APPROVED -> throw new CustomException(ErrorStatus.PAYMENT_BEING_PROCESSED);
                case COMPLETED -> {
                    log.info("[Checkout] Order {} completed in Paypal, syncing locally", existingOrder.getOrderId());
                    checkoutCompletionService.handleCompletedOrder(existingOrder, student);
                }
                default -> throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
            }
        }

        CreateOrderRequestDto createOrderRequestDto = getCreateOrderRequestDto(product);
        OrderDto response = paypalHttpClient.createOrder(studentPublicId, createOrderRequestDto);
        CheckoutOrder checkoutOrder = CheckoutOrder.builder()
                .orderId(response.id())
                .status(response.status())
                .product(product)
                .student(student)
                .build();

        checkoutOrderRepository.save(checkoutOrder);
        String href = getPayNowUrl(response);
        log.info("[Service] Checkout succeeded for member: {}, checkout: {}", studentPublicId, checkoutOrder.getPublicId());
        return href;
    }

    private static String getPayNowUrl(OrderDto orderDto) {
        return orderDto.links().stream().filter(link -> PAYER_ACTION_REL.equals(link.rel())).findFirst()
                .orElseThrow(() -> new CustomException(ErrorStatus.CANNOT_FIND_REDIRECTION_LINK)).href();
    }

    private CreateOrderRequestDto getCreateOrderRequestDto(Product product) {
        PaymentSource.PaypalPaymentSource paymentSource = new PaymentSource.PaypalPaymentSource(
                PaymentSource.PaypalPaymentSource.ExperienceContext.builder()
                        .returnUrl(frontendUrl + "/approve")
                        .cancelUrl(baseUrl + "/checkouts/cancel")
                        .userAction(PaymentSource.PaypalPaymentSource.ExperienceContext.UserAction.PAY_NOW)
                        .landingPage(PaymentSource.PaypalPaymentSource.ExperienceContext.LandingPage.LOGIN)
                        .build()
        );

        PurchaseUnit.Amount amount = new PurchaseUnit.Amount(product.getCurrencyCode(), product.getDiscountedPrice().toString());
        PurchaseUnit purchaseUnit = new PurchaseUnit(amount);
        return CreateOrderRequestDto.builder()
                .intent(PaypalOrderIntent.CAPTURE)
                .purchaseUnits(List.of(purchaseUnit))
                .paymentSource(new PaymentSource(paymentSource))
                .build();
    }

    // 단순 결제 취소 절차를 어떻게 밟아야 할지 모르겠음
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cancelCheckout(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        log.info("[Service] CancelCheckout started for order: {}", orderId);

        order.cancelOrder();
        checkoutOrderRepository.save(order);
        log.info("[Service] CancelCheckout succeeded for order: {}", orderId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void approveCheckout(String userId, String orderId) throws Exception {
        Student student = studentRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        CheckoutOrder order = checkoutOrderRepository.findByOrderIdAndStudent_Id(orderId, student.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        UUID studentPublicId = student.getPublicId();

        log.info("[Service] ApproveCheckout started for member: {}, order: {}", studentPublicId, orderId);
        if (order.getStatus() == OrderStatus.COMPLETED) {
            log.info("[Service] Order {} is already completed", orderId);
            log.info("[Service] ApproveCheckout succeeded for member: {}, order: {}", studentPublicId, orderId);
            return;
        }

        OrderStatus orderStatus = paypalHttpClient.getOrder(studentPublicId, orderId).status();
        switch (orderStatus) {
            case COMPLETED -> {
                log.info("[ApproveCheckout] Order {} completed in Paypal, syncing locally", orderId);
                checkoutCompletionService.handleCompletedOrder(order, student);
            }
            case APPROVED -> {
                OrderDto orderDto = paypalHttpClient.captureOrder(studentPublicId, orderId);
                if (orderDto.status() != OrderStatus.COMPLETED) {
                    throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
                }
                checkoutCompletionService.handleCompletedOrder(order, student);
            }
            case VOIDED -> throw new CustomException(ErrorStatus.ALREADY_VOIDED_ORDER);
            default -> throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
        }
        log.info("[Service] ApproveCheckout succeeded for member: {}, order: {}", studentPublicId, orderId);
    }
}
