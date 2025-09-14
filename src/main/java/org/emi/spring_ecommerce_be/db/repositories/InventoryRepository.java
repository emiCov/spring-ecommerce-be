package org.emi.spring_ecommerce_be.db.repositories;

import org.emi.spring_ecommerce_be.db.entities.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

}
