package dev.camilo.demo.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO Response para TicketController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TicketResponse implements Serializable {
  /**
   * id de ticket
   */
  private UUID id;
  /**
   * fecha y hora de salida del vuelo
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime departureDate;
  /**
   * fecha y hora de llegada del vuelo
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime arrivalDate;
  /**
   * fecha de seleccion de vuelo
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime purchaseDate;
  /**
   * precio del ticket
   */
  private BigDecimal price;
  /**
   * vuelo seleccionado
   */
  private FlyResponse fly;
}
