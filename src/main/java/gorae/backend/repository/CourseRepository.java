package gorae.backend.repository;

import gorae.backend.entity.Course;
import gorae.backend.entity.instructor.Instructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByPublicId(UUID publicId);

    List<Course> findByTextbook_PublicId(UUID textbookId);

    @EntityGraph(attributePaths = {"instructor", "enrollments.student"})
    List<Course> findByStartTime(Instant startTime);

    List<Course> findByTextbook_PublicIdAndStartTime(UUID textbookId, Instant startTime);

    Optional<Course> findByInstructor_IdAndStartTime(Long instructorId, Instant startTime);

    boolean existsByInstructorAndStartTime(Instructor instructor, Instant startTimeAfter);
}
