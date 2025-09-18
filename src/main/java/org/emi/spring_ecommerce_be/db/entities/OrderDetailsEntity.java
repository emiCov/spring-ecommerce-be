package org.emi.spring_ecommerce_be.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
public class OrderDetailsEntity {
  @Id
  @SequenceGenerator(
      name = "order_detail_seq_generator",
      sequenceName = "order_details_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_detail_seq_generator")
  private long id;

  private short quantity;

  private BigDecimal unitPrice;

  @Column(name = "subtotal", insertable = false, updatable = false)
  private BigDecimal subtotal;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY)
  private ProductEntity product;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public short getQuantity() {
    return quantity;
  }

  public void setQuantity(short quantity) {
    this.quantity = quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  public BigDecimal getSubtotal() {
    return subtotal;
  }

  public OrderEntity getOrder() {
    return order;
  }

  public void setOrder(OrderEntity order) {
    this.order = order;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }
}
