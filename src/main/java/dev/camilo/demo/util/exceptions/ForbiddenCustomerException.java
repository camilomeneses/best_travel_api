package dev.camilo.demo.util.exceptions;

/**
 * Exception para acceso prohibido de usuario
 */
public class ForbiddenCustomerException extends RuntimeException{

  /**
   * Mensaje para response code 403 acceso prohibido de usuario
   */
  public ForbiddenCustomerException(){
    super("This customer is blocked");
  }
}
