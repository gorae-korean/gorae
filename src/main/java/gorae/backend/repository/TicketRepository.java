package gorae.backend.repository;

import gorae.backend.entity.Student;
import gorae.backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByStudent(Student student);

    @Query("select t from Ticket t where t.status = 'ACTIVE' order by t.endTime limit 1")
    Optional<Ticket> findFirst();
}
