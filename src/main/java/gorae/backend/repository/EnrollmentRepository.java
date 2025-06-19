package gorae.backend.repository;

import gorae.backend.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent_Id(Long studentId);

    Optional<Enrollment> findByPublicId(UUID publicId);
}
