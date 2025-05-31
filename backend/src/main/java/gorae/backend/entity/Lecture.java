package gorae.backend.entity;

import gorae.backend.constant.LectureStatus;
import gorae.backend.dto.lecture.LectureDto;
import gorae.backend.entity.instructor.Instructor;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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


    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    public static Lecture schedule(String code, String url, Course course) {
        return Lecture.builder()
                .code(code)
                .url(url)
                .course(course)
                .instructor(course.getInstructor())
                .students(course.getStudents())
                .status(LectureStatus.SCHEDULED)
                .build();
    }

    public void start() {
        this.status = LectureStatus.IN_PROGRESS;
        this.actualStartTime = LocalDateTime.now();
    }

    public void end() {
        this.status = LectureStatus.ENDED;
        this.actualEndTime = LocalDateTime.now();
    }

    public void cancel() {
        this.status = LectureStatus.CANCELLED;
    }

    public LectureDto toDto() {
        List<String> emails = course.getEnrollments().stream()
                .map(enrollment -> enrollment.getStudent().getEmail())
                .toList();

        return LectureDto.builder()
                .code(code)
                .redirectUrl(url)
                .studentEmails(emails)
                .build();
    }
}
