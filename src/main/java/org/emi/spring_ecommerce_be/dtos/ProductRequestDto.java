package org.emi.spring_ecommerce_be.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public record ProductRequestDto(
    @NotEmpty(message = "name must not be null or empty") String name,
    @NotEmpty String code,
    String description,
    @NotNull Double price,
    Set<TechnicalDetailsRequestDto> technicalDetails) {}
