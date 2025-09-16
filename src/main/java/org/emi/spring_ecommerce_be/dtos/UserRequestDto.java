package org.emi.spring_ecommerce_be.dtos;

import jakarta.validation.constraints.Email;

public record UserRequestDto(@Email String email, String firstName, String lastName) {}
