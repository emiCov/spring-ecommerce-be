package org.emi.spring_ecommerce_be.db.repositories.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import org.emi.spring_ecommerce_be.db.entities.BaseEntity;

public class AuditListener {

  private static final String SYSTEM = "System";

  @PrePersist
  public void onCreate(BaseEntity entity) {
    entity.setCreated(LocalDateTime.now());
    entity.setModified(LocalDateTime.now());
    entity.setChangedBy(SYSTEM);
  }

  @PreUpdate
  public void onUpdate(BaseEntity entity) {
    entity.setModified(LocalDateTime.now());
    entity.setChangedBy(SYSTEM);
  }
}
