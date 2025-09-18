package org.emi.spring_ecommerce_be.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import org.emi.spring_ecommerce_be.db.entities.OrderStatus;

public record OrderResponseDto(
    long id,
    LocalDateTime orderDate,
    BigDecimal total,
    OrderStatus orderStatus,
    String userEmail,
    Set<OrderDetailsResponseDto> orderDetails) {}
