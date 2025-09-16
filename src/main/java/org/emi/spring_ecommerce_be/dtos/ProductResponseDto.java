package org.emi.spring_ecommerce_be.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public record ProductResponseDto(
    String name,
    String code,
    String description,
    Double price,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime modified,
    Set<TechnicalDetailsResponseDto> technicalDetails) {}
