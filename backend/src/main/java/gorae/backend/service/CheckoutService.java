package gorae.backend.service;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.constant.PaypalOrderIntent;
import gorae.backend.constant.ProductName;
import gorae.backend.dto.checkout.CheckoutRequestDto;
import gorae.backend.dto.client.paypal.PaymentSource;
import gorae.backend.dto.client.paypal.CreateOrderDto;
import gorae.backend.dto.client.paypal.CreateOrderRequestDto;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {
    @Value("${spring.baseurl}")
    private String baseUrl;

    private final PaypalHttpClient paypalHttpClient;
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
        if (!student.isFirst() && product.getName() == ProductName.FIRST_TICKET) {
            throw new CustomException(ErrorStatus.CANNOT_BUY_THE_FIRST_PRODUCT);
        }

        CreateOrderRequestDto createOrderRequestDto = getCreateOrderRequestDto(product);
        CreateOrderDto response = paypalHttpClient.createOrder(createOrderRequestDto);
        CheckoutOrder checkoutOrder = CheckoutOrder.builder()
                .orderId(response.id())
                .status(response.status())
                .product(product)
                .student(student)
                .build();

        checkoutOrderRepository.save(checkoutOrder);
        return response.links().stream().filter(link -> PAYER_ACTION_REL.equals(link.rel())).findFirst()
                .orElseThrow(() -> new CustomException(ErrorStatus.CANNOT_FIND_REDIRECTION_LINK)).href();
    }

    private CreateOrderRequestDto getCreateOrderRequestDto(Product product) {
        PaymentSource.PaypalPaymentSource paymentSource = new PaymentSource.PaypalPaymentSource(
                PaymentSource.PaypalPaymentSource.ExperienceContext.builder()
                        .returnUrl(baseUrl + "/checkouts/complete")
                        .cancelUrl(baseUrl + "/checkouts/cancel")
                        .userAction(PaymentSource.PaypalPaymentSource.ExperienceContext.UserAction.PAY_NOW)
                        .landingPage(PaymentSource.PaypalPaymentSource.ExperienceContext.LandingPage.LOGIN)
                        .build()
        );

        PurchaseUnit.Amount amount = new PurchaseUnit.Amount(product.getCurrencyCode(), product.getPrice().toString());
        PurchaseUnit purchaseUnit = new PurchaseUnit(amount);
        return CreateOrderRequestDto.builder()
                .intent(PaypalOrderIntent.CAPTURE)
                .purchaseUnits(List.of(purchaseUnit))
                .paymentSource(new PaymentSource(paymentSource))
                .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void completeCheckout(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));

        order.completeOrder();
        checkoutOrderRepository.save(order);

        Student student = order.getStudent();
        Product product = order.getProduct();
        switch (product.getName()) {
            case FIRST_TICKET -> {
                student.addFirstTicket();
                studentRepository.save(student);
            }
            case MONTHLY_TICKETS -> {
                student.addMonthlyTickets();
                studentRepository.save(student);
            }
            default -> throw new CustomException(ErrorStatus.PRODUCT_NOT_FOUND);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void cancelCheckout(String orderId) {
        CheckoutOrder order = checkoutOrderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new CustomException(ErrorStatus.ORDER_NOT_FOUND));

        order.cancelOrder();
        checkoutOrderRepository.save(order);
    }
}
