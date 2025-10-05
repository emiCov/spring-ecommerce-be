package org.emi.spring_ecommerce_be.keycloak.dtos;

public record Credentials(String type, String value, boolean temporary) {}
