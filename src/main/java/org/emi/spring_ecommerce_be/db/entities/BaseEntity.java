package org.emi.spring_ecommerce_be.db.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.emi.spring_ecommerce_be.db.repositories.listeners.AuditListener;

@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class BaseEntity {

  @NotNull
  @Column(name = "created", updatable = false)
  private LocalDateTime created;

  @NotNull
  @Column(name = "modified")
  private LocalDateTime modified;

  @NotNull
  @Column(name = "changed_by", length = 60)
  private String changedBy;

  @Version
  @Column(name = "version", nullable = false)
  private Long version;

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }

  public String getChangedBy() {
    return changedBy;
  }

  public void setChangedBy(String changedBy) {
    this.changedBy = changedBy;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
