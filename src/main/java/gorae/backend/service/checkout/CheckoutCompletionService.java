package gorae.backend.service.checkout;

import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Ticket;
import gorae.backend.repository.CheckoutOrderRepository;
import gorae.backend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckoutCompletionService {
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public void handleCompletedOrder(CheckoutOrder order) {
        order.completeOrder();
        List<Ticket> tickets = order.getStudent().addMonthlyTickets(order.getProduct());
        checkoutOrderRepository.save(order);
        ticketRepository.saveAll(tickets);
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
