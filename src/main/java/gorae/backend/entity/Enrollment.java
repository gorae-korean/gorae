package gorae.backend.entity;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.enrollment.EnrollmentDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(nullable = false)
    private Instant enrolledAt;

    private Instant droppedAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public static Enrollment addEnrollment(Student student, Course course, Ticket ticket) {
        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.course = course;
        enrollment.ticket = ticket;
        enrollment.enrolledAt = Instant.now();
        enrollment.status = EnrollmentStatus.ENROLLED;
        return enrollment;
    }

    public void cancelEnrollment() {
        status = EnrollmentStatus.DROPPED;
        droppedAt = Instant.now();
        ticket = null;
    }

    public void reEnroll(Ticket ticket) {
        this.ticket = ticket;
        this.enrolledAt = Instant.now();
        this.status = EnrollmentStatus.ENROLLED;
    }

    public EnrollmentDto toDto() {
        return EnrollmentDto.builder()
                .id(this.getPublicId())
                .enrolledAt(enrolledAt)
                .droppedAt(droppedAt)
                .course(course.toDto())
                .status(status)
                .build();
    }
}
