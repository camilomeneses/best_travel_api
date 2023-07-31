package dev.camilo.demo.infraestructure.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;

@Data
public class CurrencyDTO {
  /*mapear de JSON entrante date para ingresarlo en exchangeDate*/
  @JsonProperty(value = "date")
  private LocalDate exchangeDate;

  private Map<Currency, BigDecimal> rates;
}
