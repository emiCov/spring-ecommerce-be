package org.emi.spring_ecommerce_be.keycloak.service;

import java.util.List;
import org.emi.spring_ecommerce_be.config.security.KeycloakClient;
import org.emi.spring_ecommerce_be.keycloak.dtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

  @Value("${keycloak.clientId}")
  private String clientId;

  private final KeycloakClient keycloakClient;
  private final KeycloakTokenService keycloakTokenService;

  public KeycloakService(KeycloakClient keycloakClient, KeycloakTokenService keycloakTokenService) {
    this.keycloakClient = keycloakClient;
    this.keycloakTokenService = keycloakTokenService;
  }

  public List<KeycloakUser> getUsers() {
    return keycloakClient.getUsers();
  }

  public KeycloakUser getUserByEmail(String email) {
    List<KeycloakUser> user = keycloakClient.getUserByUsername(email, true);
    return user.get(0);
  }

  public KeycloakJwtResponse loginUser(AuthenticationRequest request) {

    return keycloakTokenService.getUserAccessToken(request.email(), request.password());
  }

  public void logoutUser(String userId) {
    keycloakClient.logoutUser(userId);
  }

  public KeycloakJwtResponse refreshToken(String refreshToken) {
    return keycloakTokenService.refreshToken(refreshToken);
  }

  public String createKeycloakUser(KeycloakUser keycloakUser) {

    List<KeycloakRole> keycloakRoles = getUserRolesMappedToClientRoles(keycloakUser.roles());

    List<KeycloakRoleRequest> keycloakRolesRequest =
        keycloakRoles.stream()
            .map(role -> new KeycloakRoleRequest(role.id().toString(), role.name()))
            .toList();

    keycloakClient.createUser(keycloakUser);
    KeycloakUser user = keycloakClient.getUserByUsername(keycloakUser.email(), true).get(0);

    keycloakClient.assignClientRolesToUser(user.id(), getClientUUID(), keycloakRolesRequest);

    return user.id();
  }

  public void updateKeycloakUser(UpdateKeycloakUser keycloakUser) {
    keycloakClient.updateUser(keycloakUser, keycloakUser.id());
  }

  public void deleteKeycloakUser(String userEmail) {
    KeycloakUser user = keycloakClient.getUserByUsername(userEmail, true).get(0);

    keycloakClient.deleteUser(user.id());
  }

  private List<KeycloakRole> getUserRolesMappedToClientRoles(List<String> roles) {
    List<KeycloakRole> keycloakRoles =
        getClientRoles().stream().filter(clientRole -> roles.contains(clientRole.name())).toList();

    if (keycloakRoles.isEmpty() || keycloakRoles.size() != roles.size()) {
      throw new IllegalArgumentException("One or more roles were not found");
    }

    return keycloakRoles;
  }

  private List<KeycloakRole> getClientRoles() {
    return keycloakClient.getClientRoles(getClientUUID());
  }

  private String getClientUUID() {
    return keycloakClient.getClientByClientId(clientId).get(0).get("id").toString();
  }
}
