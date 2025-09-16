package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.emi.spring_ecommerce_be.dtos.UserRequestDto;
import org.emi.spring_ecommerce_be.dtos.UserResponseDto;
import org.emi.spring_ecommerce_be.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @Operation(description = "Add user")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto addUser(@Valid @RequestBody UserRequestDto request) {
    return userService.addUser(request);
  }

  @PutMapping
  @Operation(description = "Update user")
  @ResponseStatus(HttpStatus.OK)
  public UserResponseDto updateUser(@Valid @RequestBody UserRequestDto request) {
    return userService.updateUser(request);
  }

  @DeleteMapping("/{email}")
  @Operation(description = "Delete user")
  @ResponseStatus(HttpStatus.OK)
  public void deleteUser(@PathVariable String email) {
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
