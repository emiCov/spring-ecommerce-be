package org.emi.spring_ecommerce_be.dtos;

import jakarta.validation.constraints.Email;
import org.emi.spring_ecommerce_be.db.entities.UserRole;

public record UserRegisterRequestDto(
        String firstName, String lastName, @Email String email, UserRole role, String password) {}
