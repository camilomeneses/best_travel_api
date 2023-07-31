package dev.camilo.demo.api.models.responses.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Clase de error para manejo de errores, se usa para dar un grupo de
 * mensajes de error en el ControllerAdvice, hereda atributos de BaseErrorResponse
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorsResponse extends BaseErrorResponse{

  private List<String> messages;
}
