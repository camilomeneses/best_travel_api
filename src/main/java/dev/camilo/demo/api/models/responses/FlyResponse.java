package dev.camilo.demo.api.models.responses;

import dev.camilo.demo.util.enums.AeroLine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO Response para TicketController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FlyResponse implements Serializable {
  /**
   * id de vuelo
   */
  private Long id;
  /**
   * coordenadas origen latitud
   */
  private Double originLat;
  /**
   * coordenadas origen longitud
   */
  private Double originLng;
  /**
   * coordenadas destino latitud
   */
  private Double destinyLat;
  /**
   * coordenadas destino longitud
   */
  private Double destinyLng;
  /**
   * nombre de origen
   */
  private String originName;
  /**
   * nombre de destino
   */
  private String destinyName;
  /**
   * precio del vuelo
   */
  private BigDecimal price;
  /**
   * nombre de aerolinea
   */
  private AeroLine aeroLine;
}
