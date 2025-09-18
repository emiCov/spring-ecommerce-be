package org.emi.spring_ecommerce_be.mappers;

import org.emi.spring_ecommerce_be.db.entities.CartItemEntity;
import org.emi.spring_ecommerce_be.dtos.CartItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

  @Mapping(target = "productCode", source = "product.code")
  @Mapping(target = "userEmail", source = "user.email")
  @Mapping(target = "subTotal", expression = "java(cartItem.getSubtotal())")
  CartItemResponseDto toCartItemResponseDto(CartItemEntity cartItem);
}
