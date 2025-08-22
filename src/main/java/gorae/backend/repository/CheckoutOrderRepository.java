package gorae.backend.repository;

import gorae.backend.entity.CheckoutOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckoutOrderRepository extends JpaRepository<CheckoutOrder, Long> {
    Optional<CheckoutOrder> findByOrderId(String orderId);

    Optional<CheckoutOrder> findByOrderIdAndStudent_Id(String orderId, Long studentId);
}
