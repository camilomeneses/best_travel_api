package dev.camilo.demo.util.constants;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecurityConstants {

  public static final String[] PUBLIC_RESOURCES = {"/fly/**", "/hotel/**", "/swagger-ui/**", "/.well-known/**, ", "/v3/api-docs/**"};
  public static final String[] USER_RESOURCES = {"/tour/**", "/ticket/**", "/reservation/**"};
  public static final String[] ADMIN_RESOURCES = {"/user/**", "/report/**"};
  public static final String LOGIN_RESOURCE = "/login";
  public static final String ADMIN_ROLE = "ROLE_ADMIN";
  public static final String USER_ROLE = "ROLE_USER";
  public static final String APPLICATION_OWNER = "Camilo Meneses Dev";
  public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  public static final String PREFIX_TOKEN = "Bearer ";
  public static final String HEADER_AUTHORIZATION = "Authorization";
}
