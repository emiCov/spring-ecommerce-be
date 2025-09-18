package org.emi.spring_ecommerce_be.dtos;

import java.math.BigDecimal;

public record OrderDetailsResponseDto(short quantity,
                                      BigDecimal unitPrice,
                                      BigDecimal subtotal,
                                      String productCode) {}
