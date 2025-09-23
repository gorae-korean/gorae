package gorae.backend.service.checkout;

import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.constant.ProductName;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Product;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import gorae.backend.repository.CheckoutOrderRepository;
import gorae.backend.repository.StudentRepository;
import gorae.backend.repository.TicketRepository;
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
    private final StudentRepository studentRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public void handleCompletedOrder(CheckoutOrder order) {
        Product product = order.getProduct();
        List<Ticket> tickets;
        if (product.getName() == ProductName.FIRST_CLASS) {
            Student student = order.getStudent();
            Ticket firstTicket = student.addFirstTicket();

            tickets = new ArrayList<>(Collections.singletonList(firstTicket));
            studentRepository.save(student);
        } else {
            tickets = order.getStudent().addMonthlyTickets(product);
            ticketRepository.saveAll(tickets);
        }

        order.completeOrder(tickets);
        checkoutOrderRepository.save(order);
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
