package org.emi.spring_ecommerce_be.services;

import java.util.List;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.db.entities.UserRole;
import org.emi.spring_ecommerce_be.db.repositories.UserRepository;
import org.emi.spring_ecommerce_be.dtos.UserRegisterRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.emi.spring_ecommerce_be.dtos.UserUpdateRequestDto;
import org.emi.spring_ecommerce_be.exceptions.ProductAlreadyExistsException;
import org.emi.spring_ecommerce_be.exceptions.UserNotFoundException;
import org.emi.spring_ecommerce_be.keycloak.dtos.*;
import org.emi.spring_ecommerce_be.keycloak.service.KeycloakService;
import org.emi.spring_ecommerce_be.mappers.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final KeycloakService keycloakService;

  public UserService(
      UserRepository userRepository, UserMapper userMapper, KeycloakService keycloakService) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.keycloakService = keycloakService;
  }

  @Transactional(readOnly = false)
  public UserResponseDto registerUser(UserRegisterRequestDto request) {
    ensureUserDoesNotExist(request.email());

    UserRole userRole = request.role() == null ? UserRole.USER : request.role();

    List<String> roles = List.of(userRole.name());

    Credentials credentials = new Credentials("password", request.password(), false);

    KeycloakUser keycloakUser =
        new KeycloakUser(
            null,
            request.email(),
            request.firstName(),
            request.lastName(),
            List.of(credentials),
            request.email(),
            false,
            true,
            roles);

    String keycloakId = keycloakService.createKeycloakUser(keycloakUser);

    UserEntity savedUser = userRepository.save(userMapper.toUserEntity(request, keycloakId));

    return userMapper.toUserResponse(savedUser);
  }

  @Transactional(readOnly = false)
  public UserResponseDto updateUser(UserUpdateRequestDto request) {
    UserEntity existingEntity = findUserByEmail(request.email());

    UpdateKeycloakUser keycloakUser =
        new UpdateKeycloakUser(
            existingEntity.getKeycloakId(),
            request.email(),
            request.firstName(),
            request.lastName(),
            request.email());

    userMapper.updateEntityFromDto(request, existingEntity);
    keycloakService.updateKeycloakUser(keycloakUser);

    return userMapper.toUserResponse(userRepository.save(existingEntity));
  }

  @Transactional(readOnly = false)
  public void deleteUserByEmail(String email) {
    keycloakService.deleteKeycloakUser(email);
    userRepository.delete(findUserByEmail(email));
  }

  public List<UserResponseDto> getUsers() {
    return userMapper.toUserResponseList(userRepository.findAll());
  }

  public UserResponseDto getUserByEmail(String email) {
    return userMapper.toUserResponse(findUserByEmail(email));
  }

  public KeycloakJwtResponse generateJwtResponse(AuthenticationRequest request) {
    KeycloakJwtResponse response = keycloakService.loginUser(request);
    UserEntity user = findUserByEmail(request.email());

    response.setEmail(user.getEmail());
    response.setRole(user.getRole());

    return response;
  }

  public ResponseEntity<Void> logoutUser() {
    keycloakService.logoutUser(SecurityContextHolder.getContext().getAuthentication().getName());

    return ResponseEntity.noContent().build();
  }

  public KeycloakJwtResponse processTokenRefresh(TokenRefreshRequest request) {
    return keycloakService.refreshToken(request.refreshToken());
  }

  private void ensureUserDoesNotExist(String email) {
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
