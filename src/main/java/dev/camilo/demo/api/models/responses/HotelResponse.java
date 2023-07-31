package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO Response para ReservationController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HotelResponse implements Serializable {
  /**
   * id de hotel
   */
  private Long id;
  /**
   * nombre de hotel
   */
  private String name;
  /**
   * direccion de hotel
   */
  private String address;
  /**
   * puntuacion de hotel
   */
  private Integer rating;
  /**
   * precio de hotel
   */
  private BigDecimal price;
}
