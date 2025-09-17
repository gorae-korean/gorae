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

    @Query("select p from Product p where p.name != 'FIRST_CLASS' or :isFirst = true " +
            "order by p.price asc, p.name asc")
    List<Product> findAllOrderByPrice(boolean isFirst);
}
