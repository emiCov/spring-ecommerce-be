package org.emi.spring_ecommerce_be.keycloak.dtos;

import jakarta.validation.constraints.NotBlank;

public record TokenRefreshRequest(@NotBlank String refreshToken) {}
