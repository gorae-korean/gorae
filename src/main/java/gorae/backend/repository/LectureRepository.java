package gorae.backend.repository;

import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import gorae.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT l FROM Lecture l WHERE l.course = :course " +
            "AND l.status IN ('SCHEDULED', 'IN_PROGRESS')")
    Optional<Lecture> findByCourse(Course course);

    Optional<Lecture> findByStudentsContainsAndCourse(Student student, Course course);
}
