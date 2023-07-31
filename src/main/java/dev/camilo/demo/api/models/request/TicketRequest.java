package dev.camilo.demo.api.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO Request para TicketController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketRequest implements Serializable {
  /**
   * id de customer
   */
  @Size(min = 18, max = 20, message = "The size have to a length between 18 and 20 characters")
  @NotBlank(message = "Id client is mandatory")
  /**
   * id de vuelo
   */
  private String idClient;
  /**
   * id de vuelo
   */
  @Positive(message = "Must be greater than 0")
  @NotNull(message = "Id fly is mandatory")
  private Long idFly;
}
