package org.emi.spring_ecommerce_be.services;

import java.math.BigDecimal;
import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.*;
import org.emi.spring_ecommerce_be.db.repositories.OrderRepository;
import org.emi.spring_ecommerce_be.dtos.OrderResponseDto;
import org.emi.spring_ecommerce_be.exceptions.OrderNotFoundException;
import org.emi.spring_ecommerce_be.mappers.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderService {

  private static final Logger log = LoggerFactory.getLogger(OrderService.class);

  private static final String ORDER_NOT_FOUND_MSG = "Order with id: %s was not found";

  private final OrderRepository orderRepository;
  private final CartItemService cartItemService;
  private final InventoryService inventoryService;
  private final OrderMapper orderMapper;
  private final AuthenticationService authenticationService;

  public OrderService(
      OrderRepository orderRepository,
      CartItemService cartItemService,
      InventoryService inventoryService,
      OrderMapper orderMapper,
      AuthenticationService authenticationService) {
    this.orderRepository = orderRepository;
    this.cartItemService = cartItemService;
    this.inventoryService = inventoryService;
    this.orderMapper = orderMapper;
    this.authenticationService = authenticationService;
  }

  @Transactional(readOnly = false)
  public OrderEntity createPendingOrder() {
    UserEntity user = authenticationService.getAuthenticatedUser();

    List<CartItemEntity> cartItems = cartItemService.getCartEntityForUser(user.getEmail());
    if (cartItems.isEmpty()) {
      throw new IllegalArgumentException("The cart is empty. Can not create any order");
    }

    OrderEntity order = new OrderEntity();
    order.setUser(user);
    order.setTotal(calculateTotal(cartItems));
    order.setOrderStatus(OrderStatus.PENDING);
    cartItems.forEach(cartItem -> order.addOrderDetail(getOrderDetail(cartItem)));

    return orderRepository.save(order);
  }

  public List<OrderResponseDto> findOrdersByUserEmail() {
    UserEntity user = authenticationService.getAuthenticatedUser();
    return orderRepository.findByUser_Email(user.getEmail()).stream()
        .map(orderMapper::toOrderResponseDto)
        .toList();
  }

  @Transactional(readOnly = false)
  public void deleteOrderById(long orderId) {
    orderRepository.delete(findOrderById(orderId));
  }

  public void confirmOrder(OrderEntity order) {
    adjustInventory(order.getOrderDetails());
    order.setOrderStatus(OrderStatus.COMPLETED);
    orderRepository.save(order);

    cartItemService.deleteCartForUser();
  }

  private void adjustInventory(List<OrderDetailsEntity> orderDetails) {
    orderDetails.forEach(
        orderDetail ->
            inventoryService.removeQuantityForProduct(
                orderDetail.getProduct().getCode(), orderDetail.getQuantity()));
  }

  public void cancelOrder(OrderEntity order) {
    order.setOrderStatus(OrderStatus.CANCELLED);
    orderRepository.save(order);
  }

  private OrderEntity findOrderById(long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(
            () -> {
              String message = String.format(ORDER_NOT_FOUND_MSG, orderId);
              log.error(message);
              return new OrderNotFoundException(message);
            });
  }

  private BigDecimal calculateTotal(List<CartItemEntity> cartItems) {
    return cartItems.stream()
        .map(CartItemEntity::getSubtotal)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private OrderDetailsEntity getOrderDetail(CartItemEntity cartItem) {
    return orderMapper.toOrderDetailsEntity(cartItem);
  }
}
