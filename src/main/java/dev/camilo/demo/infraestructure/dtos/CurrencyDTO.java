package dev.camilo.demo.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

/**
 * DTO Response que recibe la respuesta desde el API de apilayer.com
 */
@Data
public class CurrencyDTO {
  /**
   * mapear de JSON entrante date para ingresarlo en exchangeDate
   */
  @JsonProperty(value = "timestamp")
  private Long timestamp;

  /**
   * valores de las divisas
   */
  private Map<String, BigDecimal> rates;
}
