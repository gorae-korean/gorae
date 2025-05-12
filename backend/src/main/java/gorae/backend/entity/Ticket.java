package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Ticket extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne(mappedBy = "ticket")
    private Enrollment enrollment;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private LocalDateTime usedAt;

    private LocalDateTime droppedAt;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public void useTicket() {
        status = TicketStatus.USED;
        usedAt = LocalDateTime.now();
    }

    public void returnTicket() {
        status = TicketStatus.ACTIVE;
        droppedAt = LocalDateTime.now();
    }
}

