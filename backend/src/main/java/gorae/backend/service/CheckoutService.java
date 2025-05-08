package gorae.backend.service;

import gorae.backend.common.paypal.PaypalHttpClient;
import gorae.backend.dto.paypal.CreateOrderDto;
import gorae.backend.dto.paypal.CreateOrderRequestDto;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final PaypalHttpClient paypalHttpClient;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateOrderDto checkout(CreateOrderRequestDto dto) throws Exception {
        CreateOrderDto response = paypalHttpClient.createOrder(dto);
        CheckoutOrder checkoutOrder = CheckoutOrder.builder()
                .orderId(response.id())
                .status(response.status().toString())
                .build();
        orderRepository.save(checkoutOrder);
        return response;
    }
}
