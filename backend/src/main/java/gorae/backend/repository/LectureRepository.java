package gorae.backend.repository;

import gorae.backend.entity.Course;
import gorae.backend.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
    @Query("SELECT (COUNT(l) > 0) FROM Lecture l WHERE l.course = :course " +
            "AND l.status IN ('SCHEDULED', 'IN_PROGRESS')")
    boolean existsByCourse(Course course);
}
