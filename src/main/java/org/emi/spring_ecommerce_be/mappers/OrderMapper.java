package org.emi.spring_ecommerce_be.mappers;

import org.emi.spring_ecommerce_be.db.entities.CartItemEntity;
import org.emi.spring_ecommerce_be.db.entities.OrderDetailsEntity;
import org.emi.spring_ecommerce_be.db.entities.OrderEntity;
import org.emi.spring_ecommerce_be.dtos.OrderDetailsResponseDto;
import org.emi.spring_ecommerce_be.dtos.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(target = "userEmail", source = "user.email")
  @Mapping(target = "orderDetails", source = "orderDetails")
  OrderResponseDto toOrderResponseDto(OrderEntity order);

  @Mapping(target = "productCode", source = "product.code")
  OrderDetailsResponseDto toOrderDetailsResponseDto(OrderDetailsEntity orderDetails);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "subtotal", ignore = true)
  @Mapping(target = "order", ignore = true)
  @Mapping(target = "unitPrice", source = "product.price")
  OrderDetailsEntity toOrderDetailsEntity(CartItemEntity cartItem);
}
