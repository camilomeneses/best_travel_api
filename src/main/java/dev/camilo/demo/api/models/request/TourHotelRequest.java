package dev.camilo.demo.api.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO Request para TourController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourHotelRequest implements Serializable {
  public Long id;
  private Integer totalDays;
}
