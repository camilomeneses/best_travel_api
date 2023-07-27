package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO Response para TicketController
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlyPriceResponse implements Serializable {
  private BigDecimal flyPrice;
}
