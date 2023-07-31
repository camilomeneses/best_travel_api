package dev.camilo.demo.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO Response para ReservationController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationResponse implements Serializable {
  /**
   * id de reservacion
   */
  private UUID id;
  /**
   * fecha y hora de realizacion de reservacion
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime dateTimeReservation;
  /**
   * fecha inicial de reservacion
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate dateStart;
  /**
   * fecha final de reservacion
   */
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate dateEnd;
  /**
   * total de dias a reservar
   */
  private Integer totalDays;
  /**
   * precio de reservacion
   */
  private BigDecimal price;
  /**
   * hotel a reservar
   */
  private HotelResponse hotel;
}
