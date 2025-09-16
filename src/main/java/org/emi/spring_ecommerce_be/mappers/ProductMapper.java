package org.emi.spring_ecommerce_be.mappers;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.ProductEntity;
import org.emi.spring_ecommerce_be.db.entities.TechnicalDetailsEntity;
import org.emi.spring_ecommerce_be.dtos.ProductRequestDto;
import org.emi.spring_ecommerce_be.dtos.ProductResponseDto;
import org.emi.spring_ecommerce_be.dtos.TechnicalDetailsRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductResponseDto toProductResponse(ProductEntity product);

  List<ProductResponseDto> toProductResponseList(List<ProductEntity> products);

  @Mapping(target = "technicalDetails", ignore = true)
  ProductEntity toProductEntity(ProductRequestDto productRequest);

  TechnicalDetailsEntity toTechnicalDetailsEntity(
      TechnicalDetailsRequestDto technicalDetailsRequest);

  @Mapping(target = "technicalDetails", ignore = true)
  void updateEntityFromDto(ProductRequestDto dto, @MappingTarget ProductEntity product);
}
