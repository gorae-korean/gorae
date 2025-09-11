package gorae.backend.entity;

import gorae.backend.constant.CourseStatus;
import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.dto.course.CourseDto;
import gorae.backend.dto.course.InstructorCourseDto;
import gorae.backend.dto.member.StudentDto;
import gorae.backend.entity.instructor.Instructor;
import gorae.backend.entity.textbook.Textbook;
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

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    public CourseDto toDto() {
        return CourseDto.builder()
                .id(this.getPublicId())
                .level(textbook.getLevel())
                .instructor(instructor.toDto())
                .textbook(textbook.toDto())
                .startTime(startTime)
                .endTime(endTime)
                .currentCount(getCurrentCount())
                .maxCount(maxCount)
                .status(status)
                .build();
    }

    public InstructorCourseDto toInstructorCourseDto(CourseStatus courseStatus) {
        List<StudentDto> students;
        if (courseStatus == CourseStatus.CANCELLED) {
            students = null;
        } else {
            students = getStudents().stream().map(Student::toDto).toList();
        }

        return InstructorCourseDto.builder()
                .id(this.getPublicId())
                .textbook(textbook.toDto())
                .students(students)
                .startTime(startTime)
                .endTime(endTime)
                .currentCount(getCurrentCount())
                .maxCount(maxCount)
                .status(status)
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
