package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    private LocalDateTime usedAt;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    public void use() {
        LocalDateTime now = LocalDateTime.now();
        if (!isValid()) {
            throw new CustomException(ErrorStatus.TICKET_IS_NOT_VALID);
        }
        status = TicketStatus.USED;
        usedAt = now;
    }

    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return status == TicketStatus.ACTIVE &&
                !now.isBefore(startTime) &&
                !now.isAfter(endTime);
    }
}

