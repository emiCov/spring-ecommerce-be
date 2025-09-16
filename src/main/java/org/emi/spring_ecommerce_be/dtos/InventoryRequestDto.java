package org.emi.spring_ecommerce_be.dtos;

import jakarta.validation.constraints.NotEmpty;

public record InventoryRequestDto(@NotEmpty String code, long quantity) {}
