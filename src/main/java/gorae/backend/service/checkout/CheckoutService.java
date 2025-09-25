package gorae.backend.service.checkout;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.constant.paypal.PaypalOrderStatus;
import gorae.backend.constant.paypal.PaypalOrderIntent;
import gorae.backend.constant.ProductName;
import gorae.backend.dto.checkout.CheckoutOrderDto;
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
                .findByStudentAndProductAndStatus(student, product, CheckoutOrderStatus.PENDING);
        if (optionalExistingOrder.isPresent()) {
            CheckoutOrder existingOrder = optionalExistingOrder.get();

            try {
                OrderDto existingOrderDto = paypalHttpClient.getOrder(studentPublicId, existingOrder.getOrderId());
                PaypalOrderStatus actualStatus = existingOrderDto.status();

                switch (actualStatus) {
                    case PAYER_ACTION_REQUIRED -> {
                        return getPayNowUrl(existingOrderDto);
                    }
                    case APPROVED -> throw new CustomException(ErrorStatus.PAYMENT_BEING_PROCESSED);
                    case COMPLETED -> checkoutCompletionService.handleCompletedOrder(existingOrder);
                    default -> checkoutCompletionService.handleFailedOrder(existingOrder);
                }
            } catch (CustomException e) {
                if (e.getErrorStatus() == ErrorStatus.PAYPAL_API_ERROR) {
                    checkoutCompletionService.handleFailedOrder(existingOrder);
                } else {
                    throw e;
                }
            }
        }

        CreateOrderRequestDto createOrderRequestDto = getCreateOrderRequestDto(product);
        OrderDto response = paypalHttpClient.createOrder(studentPublicId, createOrderRequestDto);
        CheckoutOrder checkoutOrder = CheckoutOrder.builder()
                .orderId(response.id())
                .status(CheckoutOrderStatus.PENDING)
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
                        .cancelUrl(frontendUrl + "/pricing")
                        .shippingPreference(PaymentSource.PaypalPaymentSource.ExperienceContext.ShippingPreference.NO_SHIPPING)
                        .userAction(PaymentSource.PaypalPaymentSource.ExperienceContext.UserAction.PAY_NOW)
                        .landingPage(PaymentSource.PaypalPaymentSource.ExperienceContext.LandingPage.NO_PREFERENCE)
                        .build()
        );

        PurchaseUnit purchaseUnit = PurchaseUnit.createPurchaseUnit(product);
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

        checkoutCompletionService.handleCancelledOrder(order);
        log.info("[Service] CancelCheckout succeeded for order: {}", orderId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void approveCheckout(String userId, String orderId) throws Exception {
        Student student = studentRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        CheckoutOrder order = checkoutOrderRepository.findByOrderIdAndStudent_Id(orderId, student.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));
        if (!student.equals(order.getStudent())) {
            throw new CustomException(ErrorStatus.NO_PERMISSIONS);
        }

        UUID studentPublicId = student.getPublicId();
        log.info("[Service] ApproveCheckout started for member: {}, order: {}", studentPublicId, orderId);
        if (order.getStatus() == CheckoutOrderStatus.COMPLETED) {
            log.info("[Service] Order {} is already completed", orderId);
            log.info("[Service] ApproveCheckout succeeded for member: {}, order: {}", studentPublicId, orderId);
            return;
        }

        try {
            PaypalOrderStatus orderStatus = paypalHttpClient.getOrder(studentPublicId, orderId).status();
            switch (orderStatus) {
                case COMPLETED -> checkoutCompletionService.handleCompletedOrder(order);
                case APPROVED -> {
                    OrderDto orderDto = paypalHttpClient.captureOrder(studentPublicId, orderId);
                    if (orderDto.status() != PaypalOrderStatus.COMPLETED) {
                        checkoutCompletionService.handleFailedOrder(order);
                        throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
                    }
                    checkoutCompletionService.handleCompletedOrder(order);
                }
                case VOIDED -> throw new CustomException(ErrorStatus.ALREADY_VOIDED_ORDER);
                default -> {
                    checkoutCompletionService.handleFailedOrder(order);
                    throw new CustomException(ErrorStatus.FAILED_TO_COMPLETE_CHECKOUT);
                }
            }
        } catch (CustomException e) {
            if (e.getErrorStatus() == ErrorStatus.PAYPAL_API_ERROR) {
                checkoutCompletionService.handleFailedOrder(order);
            } else {
                throw e;
            }
        }

        log.info("[Service] ApproveCheckout succeeded for member: {}, order: {}", studentPublicId, orderId);
    }

    @Transactional(readOnly = true)
    public List<CheckoutOrderDto> getOrders(String userId) {
        long studentId = Long.parseLong(userId);
        return checkoutOrderRepository.findByStudent_Id(studentId)
                .stream().map(CheckoutOrder::toDto).toList();
    }
}
