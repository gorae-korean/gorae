package gorae.backend.entity;

import gorae.backend.constant.CheckoutOrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Table(name = "checkout_order")
public class CheckoutOrder extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String orderId;

    @Enumerated(EnumType.STRING)
    private CheckoutOrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Instant paymentDate;

    public void completeOrder() {
        status = CheckoutOrderStatus.COMPLETED;
        paymentDate = Instant.now();
    }

    public void updateStatus(CheckoutOrderStatus status) {
        this.status = status;
    }
}
