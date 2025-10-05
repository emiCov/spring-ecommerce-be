package org.emi.spring_ecommerce_be.config.security;

import feign.RequestInterceptor;
import org.emi.spring_ecommerce_be.keycloak.service.KeycloakTokenService;
import org.springframework.context.annotation.Bean;

public class KeycloakFeignConfig {

  private final KeycloakTokenService tokenService;

  public KeycloakFeignConfig(KeycloakTokenService tokenService) {
    this.tokenService = tokenService;
  }

  @Bean
  public RequestInterceptor keycloakAuthInterceptor() {
    return requestTemplate -> {
      String token = tokenService.getAccessToken();
      requestTemplate.header("Authorization", "Bearer " + token);
    };
  }
}
