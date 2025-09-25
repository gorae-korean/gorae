package gorae.backend.service.checkout;

import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.constant.ProductName;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Product;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import gorae.backend.repository.CheckoutOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckoutCompletionService {
    private final CheckoutOrderRepository checkoutOrderRepository;

    @Transactional
    public void handleCompletedOrder(CheckoutOrder order) {
        Product product = order.getProduct();
        List<Ticket> tickets;
        if (product.getName() == ProductName.FIRST_CLASS) {
            Student student = order.getStudent();
            Ticket firstTicket = student.addFirstTicket();
            tickets = new ArrayList<>(Collections.singletonList(firstTicket));
        } else {
            tickets = order.getStudent().addMonthlyTickets(product);
        }

        order.completeOrder(tickets);
        log.info("[Service] Order {} completed in Paypal, syncing locally", order.getOrderId());
    }

    @Transactional
    public void handleFailedOrder(CheckoutOrder order) {
        order.updateStatus(CheckoutOrderStatus.FAILED);
        checkoutOrderRepository.save(order);
    }

    @Transactional
    public void handleCancelledOrder(CheckoutOrder order) {
        order.updateStatus(CheckoutOrderStatus.CANCELLED);
        checkoutOrderRepository.save(order);
    }
}
