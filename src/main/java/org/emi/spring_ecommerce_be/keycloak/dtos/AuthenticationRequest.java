package org.emi.spring_ecommerce_be.keycloak.dtos;

public record AuthenticationRequest(String email, String password) {
}
