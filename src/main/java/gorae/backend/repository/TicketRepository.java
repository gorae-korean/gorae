package gorae.backend.repository;

import gorae.backend.constant.TicketStatus;
import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStatusAndStudent_IdAndEndTimeAfter(TicketStatus status, Long studentId, Instant endTimeAfter);

    @Query("select t from Ticket t where t.status = 'ACTIVE' and t.lessonRatio = :lessonRatio " +
            "and t.student = :student and t.endTime > :startTime order by t.endTime limit 1")
    Optional<Ticket> findFirstByStudentAndLessonRatio(Student student, Instant startTime, int lessonRatio);

    long countByStudent_IdAndLessonRatioAndStatusAndEndTimeAfter(Long studentId, int lessonRatio, TicketStatus status, Instant endTimeAfter);
}
