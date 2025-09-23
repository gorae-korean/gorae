package gorae.backend.entity;

import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.member.StudentDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @Builder.Default
    private List<CheckoutOrder> orders = new ArrayList<>();

    @ManyToMany(mappedBy = "students")
    @Builder.Default
    private List<Lecture> lectures = new ArrayList<>();

    public Ticket addFirstTicket() {
        Instant now = Instant.now();
        ZoneId thailandZone = ZoneId.of("Asia/Bangkok");
        LocalDateTime endOfDay = LocalDateTime.of(2025, 9, 28, 23, 59, 59);
        Instant endTime = endOfDay.atZone(thailandZone).toInstant();

        Ticket ticket = Ticket.builder()
                .student(this)
                .startTime(now)
                .endTime(endTime)
                .lessonRatio(5)
                .status(TicketStatus.ACTIVE)
                .build();

        tickets.add(ticket);
        isFirst = false;
        return ticket;
    }

    public Ticket addMonthlyTicket(Product product, Instant now) {
        Ticket ticket = Ticket.builder()
                .student(this)
                .startTime(now)
                .endTime(now.plus(product.getDuration(), ChronoUnit.DAYS))
                .lessonRatio(product.getLessonRatio())
                .status(TicketStatus.ACTIVE)
                .build();
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> addMonthlyTickets(Product product) {
        List<Ticket> tickets1 = new ArrayList<>();
        for (int i = 0; i < product.getCount(); i++) {
            tickets1.add(addMonthlyTicket(product, Instant.now()));
        }
        return tickets1;
    }

    public StudentDto toDto() {
        return new StudentDto(getName(), getPicture());
    }
}
