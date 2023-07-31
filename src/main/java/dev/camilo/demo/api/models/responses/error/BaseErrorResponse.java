package dev.camilo.demo.api.models.responses.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Clase Base para modelo de respuesta en controller para los handler de error
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseErrorResponse implements Serializable {

  private String status;
  private Integer errorCode;
}
