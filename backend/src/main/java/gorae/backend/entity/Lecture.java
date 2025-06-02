package gorae.backend.entity;

import gorae.backend.constant.LectureStatus;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.entity.instructor.Instructor;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
public class Lecture extends BaseEntity {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LectureStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @ManyToMany
    @JoinTable(
            name = "lecture_students",
            joinColumns = @JoinColumn(name = "lecture_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<Student> students = new ArrayList<>();

    @Column(nullable = false)
    private Instant scheduledStartTime;

    @Column(nullable = false)
    private Instant scheduledEndTime;

    private Instant actualStartTime;

    private Instant actualEndTime;

    public static Lecture schedule(String code, String url, Course course) {
        return Lecture.builder()
                .code(code)
                .url(url)
                .course(course)
                .instructor(course.getInstructor())
                .students(course.getStudents())
                .scheduledStartTime(course.getStartTime())
                .scheduledEndTime(course.getEndTime())
                .status(LectureStatus.SCHEDULED)
                .build();
    }

    public void start() {
        this.status = LectureStatus.IN_PROGRESS;
        this.actualStartTime = Instant.now();
    }

    public LectureDto toDto() {
        List<String> emails = students.stream().map(Student::getEmail).toList();

        return LectureDto.builder()
                .code(code)
                .redirectUrl(url)
                .studentEmails(emails)
                .build();
    }
}
