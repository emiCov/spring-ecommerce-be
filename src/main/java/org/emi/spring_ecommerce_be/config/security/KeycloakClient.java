package org.emi.spring_ecommerce_be.config.security;

import java.util.List;
import java.util.Map;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakRole;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakRoleRequest;
import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakUser;
import org.emi.spring_ecommerce_be.keycloak.dtos.UpdateKeycloakUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
    name = "keycloak",
    url = "${keycloak.baseUrl}",
    configuration = KeycloakFeignConfig.class)
public interface KeycloakClient {
  @GetMapping("${keycloak.userUri}")
  List<KeycloakUser> getUsers();

  @GetMapping("${keycloak.userUri}")
  List<KeycloakUser> getUserByUsername(
      @RequestParam("username") String username,
      @RequestParam(value = "exact", required = false) Boolean exact);

  @PostMapping("${keycloak.userUri}")
  void createUser(@RequestBody KeycloakUser user);

  @PutMapping("${keycloak.userUri}/{userId}")
  void updateUser(@RequestBody UpdateKeycloakUser user, @PathVariable String userId);

  @DeleteMapping("${keycloak.userUri}/{id}")
  void deleteUser(@PathVariable String id);

  @PostMapping("${keycloak.userUri}/{userId}/logout")
  void logoutUser(@PathVariable String userId);

  @GetMapping("${keycloak.clientUri}")
  List<Map<String, Object>> getClientByClientId(@RequestParam String clientId);

  @GetMapping("${keycloak.roleUri}")
  List<Map<String, Object>> getRealmRoles();

  @GetMapping("${keycloak.clientUri}/{clientUUID}/roles")
  List<KeycloakRole> getClientRoles(@PathVariable String clientUUID);

  @GetMapping("${keycloak.userUri}/{userId}/role-mappings/clients/{clientId}")
  List<Map<String, Object>> getClientRolesForUser(
      @PathVariable String userId, @PathVariable String clientId);

  @PostMapping("${keycloak.userUri}/{userId}/role-mappings/clients/{clientId}")
  List<Map<String, Object>> assignClientRolesToUser(
      @PathVariable String userId,
      @PathVariable String clientId,
      @RequestBody List<KeycloakRoleRequest> roles);

  @PostMapping("${keycloak.roleUri}")
  void createRole(@RequestBody Map<String, Object> role);
}
