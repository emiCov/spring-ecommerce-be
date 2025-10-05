package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.emi.spring_ecommerce_be.dtos.OrderResponseDto;
import org.emi.spring_ecommerce_be.mappers.OrderMapper;
import org.emi.spring_ecommerce_be.services.CheckoutService;
import org.emi.spring_ecommerce_be.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/checkout")
@PreAuthorize("hasRole('USER')")
public class CheckoutController {

  private final OrderService orderService;
  private final CheckoutService checkoutService;
  private final OrderMapper orderMapper;

  public CheckoutController(
      OrderService orderService, CheckoutService checkoutService, OrderMapper orderMapper) {
    this.orderService = orderService;
    this.checkoutService = checkoutService;
    this.orderMapper = orderMapper;
  }

  @PostMapping("/create")
  @Operation(
      summary = "Create an order",
      description = "Create an order with status 'PENDING' from the user's shopping cart")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponseDto createOrder() {

    return orderMapper.toOrderResponseDto(orderService.createPendingOrder());
  }

  @PostMapping("/{orderId}/complete")
  @Operation(
      summary = "Complete an order for the user",
      description =
"""
  If payment is successful, the inventory is adjusted, the status is set to 'COMPLETED' and the shopping cart is deleted.
  If payment fails, the status is set to 'CANCELLED' and the shopping cart is not deleted.
""")
  @ResponseStatus(HttpStatus.CREATED)
  public String completeOrder(@PathVariable Long orderId) {
    return checkoutService.completeOrder(orderId);
  }
}
