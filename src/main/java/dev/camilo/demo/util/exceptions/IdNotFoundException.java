package dev.camilo.demo.util.exceptions;

/**
 * Exception para cuando no se encuentra un id en una tabla determinada
 */
public class IdNotFoundException extends RuntimeException{

  /**
   * Mensaje de error para id no encontrado
   */
  private static final String ERROR_MESSAGE = "Record no exist in %s";

  /**
   * Metodo que llama a RuntimeException para lanzar mensaje
   * @param tableName
   */
  public IdNotFoundException(String tableName){
    super(String.format(ERROR_MESSAGE,tableName));
  }
}
