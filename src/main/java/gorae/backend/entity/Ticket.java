package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.ticket.TicketDto;
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

    @Column(nullable = false, updatable = false)
    private int lessonRatio;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public void useTicket() {
        status = TicketStatus.USED;
    }

    public void returnTicket() {
        status = TicketStatus.ACTIVE;
        clearTicket();
    }

    public void clearTicket() {
        enrollment = null;
    }

    public TicketDto toDto() {
        return TicketDto.builder()
                .id(getPublicId())
                .startTime(startTime)
                .endTime(endTime)
                .lessonRatio(lessonRatio)
                .build();
    }
}

