package gorae.backend.repository;

import gorae.backend.constant.CourseStatus;
import gorae.backend.entity.Course;
import gorae.backend.entity.instructor.Instructor;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Course c where c.publicId = :publicId")
    Optional<Course> findByPublicIdWithLock(UUID publicId);

    List<Course> findByTextbook_PublicId(UUID textbookId);

    @EntityGraph(attributePaths = {"instructor", "enrollments.student", "textbook.tags"})
    List<Course> findByStartTime(Instant startTime);

    List<Course> findByTextbook_PublicIdAndStartTime(UUID textbookId, Instant startTime);

    Optional<Course> findByInstructor_IdAndStartTime(Long instructorId, Instant startTime);

    boolean existsByInstructorAndStartTime(Instructor instructor, Instant startTimeAfter);

    @Query("SELECT c FROM Course c WHERE c.startTime BETWEEN :startTime AND :endTime " +
            "ORDER BY c.startTime")
    @EntityGraph(attributePaths = {"enrollments.student"})
    List<Course> findTimeTable(Instant startTime, Instant endTime);

    List<Course> findByInstructor_IdAndStatus(Long instructorId, CourseStatus status);
}
