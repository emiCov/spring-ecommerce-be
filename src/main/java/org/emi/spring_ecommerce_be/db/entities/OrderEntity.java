package org.emi.spring_ecommerce_be.db.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {

  @Id
  @SequenceGenerator(name = "order_seq_generator", sequenceName = "orders_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_generator")
  private long id;

  private LocalDateTime orderDate = LocalDateTime.now();

  private BigDecimal total;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private List<OrderDetailsEntity> orderDetails = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(LocalDateTime orderDate) {
    this.orderDate = orderDate;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public UserEntity getUser() {
    return user;
  }

  public void setUser(UserEntity user) {
    this.user = user;
  }

  public List<OrderDetailsEntity> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(List<OrderDetailsEntity> orderDetails) {
    this.orderDetails = orderDetails;
  }

  public void addOrderDetail(OrderDetailsEntity orderDetail) {
    orderDetails.add(orderDetail);
    orderDetail.setOrder(this);
  }

  public void removeOrderDetail(OrderDetailsEntity orderDetail) {
    orderDetails.remove(orderDetail);
    orderDetail.setOrder(null);
  }
}
