package gorae.backend.repository;

import gorae.backend.constant.textbook.TextbookLevel;
import gorae.backend.entity.textbook.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TextbookRepository extends JpaRepository<Textbook, Long>, JpaSpecificationExecutor<Textbook> {
    Textbook findFirstByLevel(TextbookLevel level);

    Optional<Textbook> findByPublicId(UUID publicId);
}
