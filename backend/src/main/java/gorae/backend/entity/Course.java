package gorae.backend.entity;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.entity.instructor.Instructor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "textbook_id", nullable = false)
    private Textbook textbook;

    public CourseDto toDto() {
        return CourseDto.builder()
                .title(title)
                .level(textbook.getLevel())
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public int getEnrollmentsSize() {
        return (int) enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.ENROLLED)
                .count();
    }

    public List<Student> getStudents() {
        return enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.ENROLLED)
                .map(Enrollment::getStudent)
                .toList();
    }
}
