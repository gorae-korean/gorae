package gorae.backend.repository;

import gorae.backend.constant.PaypalOrderStatus;
import gorae.backend.entity.CheckoutOrder;
import gorae.backend.entity.Product;
import gorae.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckoutOrderRepository extends JpaRepository<CheckoutOrder, Long> {
    Optional<CheckoutOrder> findByOrderId(String orderId);

    Optional<CheckoutOrder> findByOrderIdAndStudent_Id(String orderId, Long studentId);

    Optional<CheckoutOrder> findByStudentAndProductAndStatus(Student student, Product product, PaypalOrderStatus status);
}
