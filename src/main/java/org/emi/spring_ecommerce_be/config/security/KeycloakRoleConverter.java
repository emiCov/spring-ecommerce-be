package org.emi.spring_ecommerce_be.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private final String clientId;

  public KeycloakRoleConverter(@Value("${keycloak.clientId}") String clientId) {
    this.clientId = clientId;
  }

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {

    List<GrantedAuthority> authorities = new ArrayList<>();

    Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

    if (realmAccess != null && !realmAccess.isEmpty()) {
      authorities.addAll(((List<String>) realmAccess.get("roles"))
        .stream().map(SimpleGrantedAuthority::new).toList());
    }

    Map<String, Object> resourceAccess = (Map<String, Object>) jwt.getClaims().get("resource_access");
    if (resourceAccess != null && resourceAccess.get(clientId) instanceof Map<?, ?> client) {
      Object roles = client.get("roles");
      if (roles instanceof List<?>) {
        authorities.addAll(
                ((List<?>) roles)
                        .stream()
                        .map(Object::toString)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList());
      }
    }
    return authorities;
  }
}
