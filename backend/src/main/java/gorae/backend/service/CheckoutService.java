package gorae.backend.service;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.constant.OrderStatus;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.dto.paypal.PaymentSource;
import gorae.backend.dto.paypal.CreateOrderDto;
import gorae.backend.dto.paypal.CreateOrderRequestDto;
import gorae.backend.dto.paypal.PurchaseUnit;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final PaypalHttpClient paypalHttpClient;
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final StudentRepository studentRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CreateOrderDto checkout(String userId, CheckoutRequestDto dto) throws Exception {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new CustomException(ErrorStatus.PRODUCT_NOT_FOUND));

        PaymentSource.PaypalPaymentSource paymentSource = new PaymentSource.PaypalPaymentSource(
                PaymentSource.PaypalPaymentSource.ExperienceContext.builder()
                        .returnUrl("http://localhost:8080/api/checkouts/success")
                        .cancelUrl("http://localhost:8080/api/checkouts/cancel")
                        .userAction(PaymentSource.PaypalPaymentSource.ExperienceContext.UserAction.PAY_NOW)
                        .landingPage(PaymentSource.PaypalPaymentSource.ExperienceContext.LandingPage.LOGIN)
                        .build()
        );

        PurchaseUnit.Amount amount = new PurchaseUnit.Amount(product.getCurrencyCode(), product.getPrice().toString());
        PurchaseUnit purchaseUnit = new PurchaseUnit(amount);
        CreateOrderRequestDto createOrderRequestDto = CreateOrderRequestDto.builder()
                .intent(dto.intent())
                .purchaseUnits(List.of(purchaseUnit))
                .paymentSource(new PaymentSource(paymentSource))
                .build();

        CreateOrderDto response = paypalHttpClient.createOrder(createOrderRequestDto);
        Student student = studentRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new CustomException(ErrorStatus.MEMBER_NOT_FOUND));
        CheckoutOrder checkoutOrder = CheckoutOrder.builder()
                .orderId(response.id())
                .status(response.status())
                .product(product)
                .student(student)
                .build();

        checkoutOrderRepository.save(checkoutOrder);
        return response;
    }

    @Transactional
    public void checkoutSuccess(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));

        order.setStatus(OrderStatus.COMPLETED);
        order.setPaymentDate(LocalDateTime.now());
        checkoutOrderRepository.save(order);

        Student student = order.getStudent();
        Product product = order.getProduct();
        // TODO: Product 종류 추가 및 아래 코드 추가
        if (product.getName().equals("First ticket")) {
            student.addTicket();
            studentRepository.save(student);
        } else {
            throw new CustomException(ErrorStatus.PRODUCT_NOT_FOUND);
        }
    }
}
