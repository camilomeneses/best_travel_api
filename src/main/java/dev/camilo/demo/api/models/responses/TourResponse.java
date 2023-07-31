package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO Response para TourController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourResponse implements Serializable {
  /**
   * id de tour
   */
  private Long id;
  /**
   * conjunto de ticketes de vuelo de tour
   */
  private Set<UUID> ticketsIds;
  /**
   * conjunto de reservaciones de hoteles de tour
   */
  private Set<UUID> reservationIds;
}
