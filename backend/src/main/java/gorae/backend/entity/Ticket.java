package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
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
public class Ticket extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(mappedBy = "ticket")
    private Enrollment enrollment;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    private Instant usedAt;

    private Instant droppedAt;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public void useTicket() {
        status = TicketStatus.USED;
        usedAt = Instant.now();
    }

    public void returnTicket() {
        status = TicketStatus.ACTIVE;
        droppedAt = Instant.now();
    }
}

