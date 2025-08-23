package gorae.backend.service.checkout;

import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Student;
import gorae.backend.repository.CheckoutOrderRepository;
import gorae.backend.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CheckoutCompletionService {
    private final CheckoutOrderRepository checkoutOrderRepository;
    private final StudentRepository studentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleCompletedOrder(CheckoutOrder order, Student student) {
        order.completeOrder();
        student.addMonthlyTickets(order.getProduct().getCount());
        checkoutOrderRepository.save(order);
        studentRepository.save(student);
    }
}
