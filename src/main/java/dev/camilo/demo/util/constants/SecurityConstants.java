package dev.camilo.demo.util.constants;

public class SecurityConstants {
  private static final String[] PUBLIC_RESOURCES = {"/fly/**", "/hotel/**", "/swagger-ui/**", "/.well-known/**, ", "/v3/api-docs/**"};
  private static final String[] USER_RESOURCES = {"/tour/**", "/ticket/**", "/reservation/**"};
  private static final String[] ADMIN_RESOURCES = {"/user/**", "/report/**"};
  private static final String LOGIN_RESOURCE = "/login";
}
