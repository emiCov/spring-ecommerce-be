package org.emi.spring_ecommerce_be.mappers;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.InventoryEntity;
import org.emi.spring_ecommerce_be.dtos.InventoryRequestDto;
import org.emi.spring_ecommerce_be.dtos.InventoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
  InventoryResponseDto toInventoryResponse(InventoryEntity inventory);

  InventoryEntity toInventoryEntity(InventoryRequestDto request);

  List<InventoryResponseDto> toInventoryResponseList(List<InventoryEntity> inventories);

  void updateEntityFromDto(InventoryRequestDto dto, @MappingTarget InventoryEntity inventory);
}
