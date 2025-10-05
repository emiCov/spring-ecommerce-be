package org.emi.spring_ecommerce_be.keycloak.service;

import java.util.Map;
import org.emi.spring_ecommerce_be.config.security.KeycloakAuthClient;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakJwtResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class KeycloakTokenService {

  private final KeycloakAuthClient authClient;
  private final String clientId;
  private final String clientSecret;

  public KeycloakTokenService(
      KeycloakAuthClient authClient,
      @Value("${keycloak.clientId}") String clientId,
      @Value("${keycloak.clientSecret}") String clientSecret) {
    this.authClient = authClient;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  public String getAccessToken() {
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "client_credentials");
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);

    Map<String, Object> response = authClient.getToken(form);
    return (String) response.get("access_token");
  }

  public KeycloakJwtResponse getUserAccessToken(String username, String password) {
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "password");
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    form.add("username", username);
    form.add("password", password);

    return authClient.getUserToken(form);
  }

  public KeycloakJwtResponse refreshToken(String refreshToken) {
    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("client_id", clientId);
    form.add("client_secret", clientSecret);
    form.add("grant_type", "refresh_token");
    form.add("refresh_token", refreshToken);

    return authClient.getUserToken(form);
  }
}
