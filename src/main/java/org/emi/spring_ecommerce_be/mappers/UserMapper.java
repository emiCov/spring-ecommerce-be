package org.emi.spring_ecommerce_be.mappers;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.dtos.UserRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponseDto toUserResponse(UserEntity user);

  UserEntity toUserEntity(UserRequestDto request);

  List<UserResponseDto> toUserResponseList(List<UserEntity> users);

  void updateEntityFromDto(UserRequestDto dto, @MappingTarget UserEntity user);
}
