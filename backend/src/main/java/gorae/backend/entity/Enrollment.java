package gorae.backend.entity;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.enrollment.EnrollmentDto;
import gorae.backend.exception.CustomException;
import gorae.backend.exception.ErrorStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public static Enrollment addEnrollment(Student student, Course course) {
        boolean alreadyEnrolled = course.getEnrollments().stream()
                .anyMatch(enrollment -> enrollment.getStudent().equals(student)
                        && enrollment.getStatus() == EnrollmentStatus.ENROLLED);
        if (alreadyEnrolled) {
            throw new CustomException(ErrorStatus.ALREADY_ENROLLED);
        }
        if (course.getEnrollmentsSize() >= 4) {
            throw new CustomException(ErrorStatus.COURSE_IS_FULL);
        }
        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.course = course;
        enrollment.enrolledAt = LocalDateTime.now();
        enrollment.status = EnrollmentStatus.ENROLLED;
        return enrollment;
    }

    public void cancelEnrollment() {
        status = EnrollmentStatus.DROPPED;
    }

    public EnrollmentDto toDto() {
        return EnrollmentDto.builder()
                .id(id)
                .enrolledAt(enrolledAt)
                .course(course.toDto())
                .status(status)
                .build();
    }
}
