package gorae.backend.repository;

import gorae.backend.constant.TicketStatus;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatusAndStudent_Id(TicketStatus status, Long studentId);

    @Query("select t from Ticket t where t.status = 'ACTIVE' and t.lessonRatio = :lessonRatio " +
            "and t.student = :student order by t.endTime limit 1")
    Optional<Ticket> findFirstByStudentAndLessonRatio(Student student, int lessonRatio);

    long countByStudent_IdAndLessonRatio(Long id, int lessonRatio);
}
