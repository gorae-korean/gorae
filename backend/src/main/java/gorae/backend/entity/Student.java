package gorae.backend.entity;

import gorae.backend.constant.MemberRole;
import gorae.backend.constant.TicketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Student extends Member {
    @Column(nullable = false)
    @Builder.Default
    private boolean isFirst = true;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<CheckoutOrder> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    private List<Lecture> lectures;

    @PrePersist
    public void prePersist() {
        super.setRole(MemberRole.STUDENT);
    }

    public void addMonthlyTicket() {
        Instant now = Instant.now();
        Ticket ticket = Ticket.builder()
                .student(this)
                .startTime(now)
                .endTime(now.atZone(ZoneOffset.UTC).plusMonths(1).toInstant())
                .status(TicketStatus.ACTIVE)
                .build();
        tickets.add(ticket);
    }

    public void addMonthlyTickets() {
        for (int i = 0; i < 8; i++) {
            addMonthlyTicket();
        }
    }

    public void addFirstTicket() {
        isFirst = false;
        addMonthlyTicket();
    }
}
