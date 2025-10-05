package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.emi.spring_ecommerce_be.keycloak.dtos.AuthenticationRequest;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakJwtResponse;
import org.emi.spring_ecommerce_be.keycloak.dtos.TokenRefreshRequest;
import org.emi.spring_ecommerce_be.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

  private final UserService userService;

  public AuthenticationController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/login")
  @Operation(summary = "Login a user", description = "Authenticate a user and return a JWT token")
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<KeycloakJwtResponse> loginUser(@RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(userService.generateJwtResponse(request));
  }

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping("/logout")
  @Operation(summary = "Logout a user")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<Void> logoutUser() {
    return userService.logoutUser();
  }

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping("/refresh")
  @Operation(
      summary = "Retrieve new tokens",
      description = "Retrieve new access & refresh token pair using a valid refresh token")
  @PreAuthorize("isAuthenticated()")
  ResponseEntity<KeycloakJwtResponse> refreshToken(@RequestBody TokenRefreshRequest request) {
    return ResponseEntity.ok(userService.processTokenRefresh(request));
  }
}
