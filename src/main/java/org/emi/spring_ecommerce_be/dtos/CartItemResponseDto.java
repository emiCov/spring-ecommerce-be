package org.emi.spring_ecommerce_be.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

public record CartItemResponseDto(
    String productCode, Short quantity, String userEmail, @JsonIgnore BigDecimal subTotal) {}
