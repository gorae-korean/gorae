package gorae.backend.repository;

import gorae.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByPublicId(UUID publicId);

    @Query("SELECT p FROM Product p WHERE p.isAvailable = TRUE")
    List<Product> findAvailable();
}
