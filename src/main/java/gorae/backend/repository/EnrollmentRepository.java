package gorae.backend.repository;

import gorae.backend.constant.EnrollmentStatus;
import gorae.backend.entity.Course;
import gorae.backend.entity.Enrollment;
import gorae.backend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
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

    boolean existsByStudentAndCourse_StartTimeAndStatus(Student student, Instant courseStartTime, EnrollmentStatus status);

    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

    List<Enrollment> findByCourseAndStudentIn(Course course, Collection<Student> students);
}
