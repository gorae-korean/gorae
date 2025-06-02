package gorae.backend.repository;

import gorae.backend.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTextbook_Id(Long textbookId);

    @EntityGraph(attributePaths = {"instructor", "enrollments.student"})
    List<Course> findByStartTime(Instant startTime);

    List<Course> findByTextbook_IdAndStartTime(Long textbookId, Instant startTime);

    Optional<Course> findByInstructor_IdAndStartTime(Long instructorId, Instant startTime);
}
