package gorae.backend.repository;

import gorae.backend.entity.CheckoutOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CheckoutOrder, Long> {
}
