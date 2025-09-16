package org.emi.spring_ecommerce_be.db.repositories;

import java.util.List;
import java.util.Optional;
import org.emi.spring_ecommerce_be.db.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  @Query(
      """
            SELECT p FROM ProductEntity p
            JOIN FETCH p.technicalDetails td
            """)
  List<ProductEntity> getAllProducts();

  Optional<ProductEntity> findByCode(String code);

  boolean existsByCode(String code);
}
