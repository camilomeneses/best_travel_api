package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO Response para ReservationController
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelPriceResponse implements Serializable {
  private BigDecimal hotelPrice;
}
