package org.emi.spring_ecommerce_be.db.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItemEntity extends BaseEntity {

  @Id
  @SequenceGenerator(
      name = "cart_item_seq_generator",
      sequenceName = "cart_items_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq_generator")
  private long id;

  private short quantity;

  @ManyToOne private UserEntity user;

  @ManyToOne private ProductEntity product;

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

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  public BigDecimal getSubtotal() {
    return product.getPrice().multiply(BigDecimal.valueOf(quantity));
  }
}
