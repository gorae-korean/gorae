package gorae.backend.entity;

import gorae.backend.constant.CheckoutOrderStatus;
import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.checkout.CheckoutOrderDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();

    private Instant paymentDate;

    public CheckoutOrderDto toDto() {
        return CheckoutOrderDto.builder()
                .id(getPublicId())
                .product(product.toSummaryDto())
                .currentCount(getCurrentTicketCount())
                .paymentDate(paymentDate)
                .endDate(tickets.isEmpty() ? null : tickets.getFirst().getEndTime())
                .status(status)
                .build();
    }

    public void completeOrder(List<Ticket> tickets) {
        status = CheckoutOrderStatus.COMPLETED;
        paymentDate = Instant.now();
        this.tickets = tickets;

        for (Ticket ticket : tickets) {
            ticket.setOrder(this);
        }
    }

    public void updateStatus(CheckoutOrderStatus status) {
        this.status = status;
    }

    public int getCurrentTicketCount() {
        return Math.toIntExact(tickets.stream().filter(
                ticket -> ticket.getStatus() == TicketStatus.ACTIVE).count());
    }
}
