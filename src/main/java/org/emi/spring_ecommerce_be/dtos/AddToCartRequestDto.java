package org.emi.spring_ecommerce_be.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AddToCartRequestDto(@NotBlank String productCode, @Positive short quantity) {}
