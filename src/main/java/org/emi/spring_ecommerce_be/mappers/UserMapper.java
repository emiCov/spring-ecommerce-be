package org.emi.spring_ecommerce_be.mappers;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.dtos.UserRegisterRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.emi.spring_ecommerce_be.dtos.UserUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponseDto toUserResponse(UserEntity user);

  @Mapping(target = "keycloakId", expression = "java(keycloakId)")
  UserEntity toUserEntity(UserRegisterRequestDto request, String keycloakId);

  List<UserResponseDto> toUserResponseList(List<UserEntity> users);

  void updateEntityFromDto(UserUpdateRequestDto dto, @MappingTarget UserEntity user);
}
