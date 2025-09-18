package gorae.backend.repository;

import gorae.backend.entity.Course;
import gorae.backend.entity.Enrollment;
import gorae.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_Id(Long studentId);

    Optional<Enrollment> findByPublicId(UUID publicId);

    @Query("select count(e) > 0 from Enrollment e where e.student = :student and e.status = 'ENROLLED' and " +
            "e.course.startTime < :endTime and e.course.endTime > :startTime")
    boolean existsByOverlappingTime(Student student, Instant startTime, Instant endTime);

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    List<Enrollment> findByCourseAndStudentIn(Course course, Collection<Student> students);
}
