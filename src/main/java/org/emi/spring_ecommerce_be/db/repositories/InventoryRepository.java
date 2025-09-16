package org.emi.spring_ecommerce_be.db.repositories;

import org.emi.spring_ecommerce_be.db.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {
  Optional<InventoryEntity> findByCode(String code);

    boolean existsByCode(String code);
}
