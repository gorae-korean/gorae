package gorae.backend.entity;

import gorae.backend.constant.MemberRole;
import gorae.backend.constant.TicketStatus;
import gorae.backend.dto.member.StudentDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
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

    @PrePersist
    public void prePersist() {
        super.setRole(MemberRole.STUDENT);
    }

    public Ticket addFirstTicket(Product product) {
        Ticket ticket = addMonthlyTicket(product, Instant.now());
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
