package org.emi.spring_ecommerce_be.services;

import java.util.Map;
import org.emi.spring_ecommerce_be.db.entities.UserEntity;
import org.emi.spring_ecommerce_be.db.repositories.UserRepository;
import org.emi.spring_ecommerce_be.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;

  public AuthenticationService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserEntity getAuthenticatedUser() {

    JwtAuthenticationToken token =
        (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

    Jwt principal = (Jwt) token.getPrincipal();
    Map<String, Object> claims = principal.getClaims();
    String email = (String) claims.get("email");

    return userRepository
        .findByEmail(email)
        .orElseThrow(
            () -> new UserNotFoundException(String.format("User with email: %s not found", email)));
  }
}
