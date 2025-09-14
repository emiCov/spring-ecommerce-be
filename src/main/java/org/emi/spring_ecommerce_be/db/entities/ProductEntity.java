package org.emi.spring_ecommerce_be.db.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

  @Id
  @SequenceGenerator(name = "product_seq_generator", sequenceName = "product_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq_generator")
  private long id;

  private String name;

  private String code;

  private String description;

  private BigDecimal price;

  @OneToMany(
      mappedBy = "product",
      cascade = CascadeType.PERSIST,
      orphanRemoval = true)
  private List<TechnicalDetailsEntity> technicalDetails = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public List<TechnicalDetailsEntity> getTechnicalDetails() {
    return technicalDetails;
  }

  public void setTechnicalDetails(
      List<TechnicalDetailsEntity> technicalDetails) {
    this.technicalDetails = technicalDetails;
  }

  public void addTechnicalDetail(TechnicalDetailsEntity technicalDetail) {
    technicalDetails.add(technicalDetail);
    technicalDetail.setProduct(this);
  }

  public void removeTechnicalDetail(TechnicalDetailsEntity technicalDetail) {
    technicalDetails.remove(technicalDetail);
    technicalDetail.setProduct(null);
  }
}
