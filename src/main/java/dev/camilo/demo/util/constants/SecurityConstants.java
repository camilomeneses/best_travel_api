package dev.camilo.demo.util.constants;

public class SecurityConstants {
  public static final String[] PUBLIC_RESOURCES = {"/fly/**", "/hotel/**", "/swagger-ui/**", "/.well-known/**, ", "/v3/api-docs/**"};
  public static final String[] USER_RESOURCES = {"/tour/**", "/ticket/**", "/reservation/**"};
  public static final String[] ADMIN_RESOURCES = {"/user/**", "/report/**"};
  public static final String LOGIN_RESOURCE = "/login";
  public static final String ADMIN_ROLE = "write";
  public static final String APPLICATION_OWNER = "Camilo Meneses Dev";
}
