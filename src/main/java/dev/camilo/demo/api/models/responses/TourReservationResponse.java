package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourReservationResponse implements Serializable {
  /**
   * id de reservation
   */
  private UUID reservationId;
}
