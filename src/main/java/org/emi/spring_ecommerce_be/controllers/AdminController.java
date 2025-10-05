package org.emi.spring_ecommerce_be.controllers;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakUser;
import org.emi.spring_ecommerce_be.keycloak.service.KeycloakService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

  private final KeycloakService keycloakService;

  public AdminController(KeycloakService keycloakService) {
    this.keycloakService = keycloakService;
  }

  @GetMapping
  @Operation(summary = "Get all users from Keycloak")
  @ResponseStatus(HttpStatus.OK)
  public List<KeycloakUser> getUsers() {
    return keycloakService.getUsers();
  }

  @GetMapping("/{email}")
  @Operation(summary = "Get a user from Keycloak by user's email")
  @ResponseStatus(HttpStatus.OK)
  public KeycloakUser getUserByEmail(@PathVariable String email) {
    return keycloakService.getUserByEmail(email);
  }
}
