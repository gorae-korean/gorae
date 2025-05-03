package gorae.backend.entity;

import gorae.backend.entity.constant.EnrollmentStatus;
import gorae.backend.entity.dto.enrollment.EnrollmentDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
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

    @Column(nullable = false)
    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;

    public static Enrollment createEnrollment(Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.student = student;
        enrollment.course = course;
        enrollment.enrolledAt = LocalDateTime.now();
        enrollment.status = EnrollmentStatus.ENROLLED;
        return enrollment;
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
