package gorae.backend.entity;

import gorae.backend.entity.constant.CourseLevel;
import gorae.backend.entity.constant.EnrollmentStatus;
import gorae.backend.entity.dto.course.CourseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {
    @Enumerated(value = EnumType.STRING)
    private CourseLevel level;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

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
                .level(level)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    public int getEnrollmentsSize() {
        return (int) enrollments.stream()
                .filter(enrollment -> enrollment.getStatus() == EnrollmentStatus.ENROLLED)
                .count();
    }
}
