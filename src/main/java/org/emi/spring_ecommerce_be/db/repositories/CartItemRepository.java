package org.emi.spring_ecommerce_be.db.repositories;

import org.emi.spring_ecommerce_be.db.entities.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
  Optional<CartItemEntity> findByUser_EmailAndProduct_Code(String email, String code);

  List<CartItemEntity> findByUser_Email(String email);

  long deleteByUser_EmailAndProduct_Code(String email, String code);
}
