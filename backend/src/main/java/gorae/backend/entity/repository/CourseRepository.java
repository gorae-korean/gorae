package gorae.backend.entity.repository;

import gorae.backend.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTextbook_Id(Long textbookId);

    List<Course> findByStartTime(LocalDateTime startTime);

    List<Course> findByTextbook_IdAndStartTime(Long textbookId, LocalDateTime startTime);
}
