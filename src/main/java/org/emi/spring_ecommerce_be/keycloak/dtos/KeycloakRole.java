package org.emi.spring_ecommerce_be.keycloak.dtos;

import java.util.UUID;

public record KeycloakRole(
        UUID id,
        String name,
        String description,
        boolean composite,
        boolean clientRole,
        UUID containerId) {}

