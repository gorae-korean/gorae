package gorae.backend.repository;

import gorae.backend.constant.TextbookLevel;
import gorae.backend.entity.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long>, JpaSpecificationExecutor<Textbook> {
    Textbook findFirstByLevel(TextbookLevel level);
}
