package org.emi.spring_ecommerce_be.config.security;

import java.util.Map;

import org.emi.spring_ecommerce_be.keycloak.dtos.KeycloakJwtResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloak-auth", url = "${keycloak.baseUrl}")
public interface KeycloakAuthClient {
  @PostMapping(
      value = "${keycloak.tokenUri}",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  Map<String, Object> getToken(@RequestBody MultiValueMap<String, String> form);

  @PostMapping(
      value = "${keycloak.tokenUri}",
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  KeycloakJwtResponse getUserToken(@RequestBody MultiValueMap<String, String> form);
}
