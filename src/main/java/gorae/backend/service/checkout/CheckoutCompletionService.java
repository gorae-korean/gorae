package gorae.backend.service.checkout;

import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Student;
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
    public void handleCompletedOrder(CheckoutOrder order, Student student) {
        order.completeOrder();
        List<Ticket> tickets = student.addMonthlyTickets(order.getProduct().getCount());
        checkoutOrderRepository.save(order);
        ticketRepository.saveAll(tickets);
    }
}
