package dev.camilo.demo.util.exceptions;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * Exception para cuando no se encuentra un id en una tabla determinada
 */
public class UsernameNotFoundException extends InternalAuthenticationServiceException {
  /**
   * Mensaje de error para user no encontrado
   */
  private static final String ERROR_MESSAGE = "User no exist in %s";

  /**
   * constructor con mensaje de error que va al padre
   * @param documentName String
   */
  public UsernameNotFoundException(String documentName) {
    super(String.format(ERROR_MESSAGE,documentName));
  }
}
