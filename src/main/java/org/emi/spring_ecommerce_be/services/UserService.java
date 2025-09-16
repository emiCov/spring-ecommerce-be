package org.emi.spring_ecommerce_be.services;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.db.repositories.UserRepository;
import org.emi.spring_ecommerce_be.dtos.UserRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.emi.spring_ecommerce_be.exceptions.ProductAlreadyExistsException;
import org.emi.spring_ecommerce_be.exceptions.UserNotFoundException;
import org.emi.spring_ecommerce_be.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  @Transactional(readOnly = false)
  public UserResponseDto addUser(UserRequestDto request) {
    validateRequest(request);
    return userMapper.toUserResponse(userRepository.save(userMapper.toUserEntity(request)));
  }

  @Transactional(readOnly = false)
  public UserResponseDto updateUser(UserRequestDto request) {
    UserEntity existingEntity = findUserByEmail(request.email());
    userMapper.updateEntityFromDto(request, existingEntity);

    return userMapper.toUserResponse(userRepository.save(existingEntity));
  }

  @Transactional(readOnly = false)
  public void deleteUserByEmail(String email) {
    userRepository.delete(findUserByEmail(email));
  }

  public List<UserResponseDto> getUsers() {
    return userMapper.toUserResponseList(userRepository.findAll());
  }

  public UserResponseDto getUserByEmail(String email) {
    return userMapper.toUserResponse(findUserByEmail(email));
  }

  private void validateRequest(UserRequestDto userRequest) {
    String email = userRequest.email();
    if (userRepository.existsByEmail(email)) {
      log.error("User with email: {} already exist. User not created.", email);
      throw new ProductAlreadyExistsException(
          String.format("User with email: %s already exists", email));
    }
  }

  private UserEntity findUserByEmail(String email) {
    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> {
              log.error("User with email: {} not found", email);
              return new UserNotFoundException(
                  String.format("User with email: %s not found", email));
            });
  }
}
