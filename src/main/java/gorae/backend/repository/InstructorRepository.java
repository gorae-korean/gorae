package gorae.backend.repository;

import gorae.backend.entity.instructor.Instructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    @Override
    @EntityGraph(attributePaths = {"availabilities", "unavailableDates"})
    @NonNull
    List<Instructor> findAll();

    @Query("select i from Instructor i where i.email not in ('kmh24631@gmail.com', 'pristo2463@gmail.com')")
    List<Instructor> findAllExceptMe();
}
