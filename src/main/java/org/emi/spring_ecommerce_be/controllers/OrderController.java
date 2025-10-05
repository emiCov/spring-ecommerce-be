package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.OrderResponseDto;
import org.emi.spring_ecommerce_be.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@PreAuthorize("hasRole('USER')")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping
  @Operation(summary = "Get orders for user")
  @ResponseStatus(HttpStatus.OK)
  public List<OrderResponseDto> findOrdersByUserEmail() {
    return orderService.findOrdersByUserEmail();
  }

  @DeleteMapping("/{orderId}")
  @Operation(summary = "Delete order by id")
  @ResponseStatus(HttpStatus.OK)
  public void deleteOrderById(@PathVariable long orderId) {
    orderService.deleteOrderById(orderId);
  }
}
