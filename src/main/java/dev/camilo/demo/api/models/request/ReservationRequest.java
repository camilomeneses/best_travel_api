package dev.camilo.demo.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO Request para ReservationController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReservationRequest implements Serializable {
  private String idClient;
  private Long idHotel;
  private Integer totalDays;
}
