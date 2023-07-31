package dev.camilo.demo.api.models.responses.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Clase de error para manejo de errores, se usa para dar el mensaje de error en el
 * ControllerAdvice, hereda atributos de BaseErrorResponse
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends BaseErrorResponse{
  private String message;
}
