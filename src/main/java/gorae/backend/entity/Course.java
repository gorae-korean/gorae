package gorae.backend.entity;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.entity.instructor.Instructor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Course extends BaseEntity {
    @Column(nullable = false, updatable = false)
    private Instant startTime;

    @Column(nullable = false, updatable = false)
    private Instant endTime;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "textbook_id")
    private Textbook textbook;

    @Column(nullable = false, updatable = false)
    private int maxCount;

    public CourseDto toDto() {
        return CourseDto.builder()
                .id(this.getPublicId())
                .level(textbook.getLevel())
                .startTime(startTime)
                .endTime(endTime)
                .maxCount(maxCount)
                .build();
    }

    public int getCurrentCount() {
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
