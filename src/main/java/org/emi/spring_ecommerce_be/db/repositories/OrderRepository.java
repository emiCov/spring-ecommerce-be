package org.emi.spring_ecommerce_be.db.repositories;

import org.emi.spring_ecommerce_be.db.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  List<OrderEntity> findByUser_Email(String email);
}
