package org.emi.spring_ecommerce_be.keycloak.dtos;

import jakarta.validation.constraints.Email;

public record UpdateKeycloakUser(
    String id, String username, String firstName, String lastName, @Email String email) {}
