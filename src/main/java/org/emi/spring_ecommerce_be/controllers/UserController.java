package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.UserRegisterRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.emi.spring_ecommerce_be.dtos.UserUpdateRequestDto;
import org.emi.spring_ecommerce_be.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register/user")
  @Operation(description = "Register a new user")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRegisterRequestDto request) {
    return ResponseEntity.ok(userService.registerUser(request));
  }

  @PutMapping
  @Operation(description = "Update user")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('USER')")
  public UserResponseDto updateUser(@Valid @RequestBody UserUpdateRequestDto request) {
    return userService.updateUser(request);
  }

  @DeleteMapping("/{email}")
  @Operation(description = "Delete a user by email")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteUserByEmail(@PathVariable String email) {
    userService.deleteUserByEmail(email);
  }

  @GetMapping
  @Operation(description = "Get all users")
  @ResponseStatus(HttpStatus.OK)
  public List<UserResponseDto> getUsers() {
    return userService.getUsers();
  }

  @GetMapping("/{email}")
  @Operation(description = "Get user by email")
  @ResponseStatus(HttpStatus.OK)
  public UserResponseDto getUserByEmail(@PathVariable String email) {
    return userService.getUserByEmail(email);
  }
}
