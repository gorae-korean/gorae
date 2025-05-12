package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("STUDENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Student extends Member {
    @Column(nullable = false)
    private boolean isFirst;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<CheckoutOrder> orders = new ArrayList<>();

    public void addTicket() {
        LocalDateTime now = LocalDateTime.now();
        Ticket ticket = Ticket.builder()
                .student(this)
                .startTime(now)
                .endTime(now.plusMonths(1))
                .status(TicketStatus.ACTIVE)
                .build();
        tickets.add(ticket);
    }
}
