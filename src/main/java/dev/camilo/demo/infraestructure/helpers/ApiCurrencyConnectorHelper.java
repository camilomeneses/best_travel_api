package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.entities.CurrencyEntity;
import dev.camilo.demo.domain.repositories.CurrencyRepository;
import dev.camilo.demo.infraestructure.dtos.CurrencyDTO;
import dev.camilo.demo.util.constants.CurrencyConstanst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * armado de llamado a la api y guardado de datos a la tabla currency_data
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ApiCurrencyConnectorHelper {
  //componentes inyectados
  /**
   * webClient para currency
   */
  private final WebClient currencyWebClient;
  /**
   * acceso a datos para tabla currency_data
   */
  private final CurrencyRepository currencyRepository;

  /**
   * base de la conversion (USD)
   */
  @Value(value = "${api.base-currency}")
  private String baseCurrency;

  /**
   * divisas a convertir (todas)
   */
  @Value(value = "${api.symbols-currency}")
  private String symbolsCurrency;

  /**
   * Metodo para llamar a la api y traer las divisas y timestamp
   * @return CurrencyDTO
   */
  public CurrencyDTO getCurrencyRates() {
    return this.currencyWebClient
        .get()
        .uri(uri ->
            uri.path(CurrencyConstanst.CURRENCY_PATH)
                .query(CurrencyConstanst.SYMBOL_CURRENCY_QUERY_PARAM)
                .query(CurrencyConstanst.BASE_CURRENCY_QUERY_PARAM)
                .build(symbolsCurrency, baseCurrency))
        .retrieve()
        .bodyToMono(CurrencyDTO.class)
        .block();
  }

  /**
   * Metodo que toma el currencyDTO recorre todos sus rates y hace update de las divisas
   * basados en el nombre de la misma en la tabla de currency_data
   * @param currencyDTO CurrencyDTO
   */
  public void saveCurrencyData(CurrencyDTO currencyDTO) {
    Long timestamp = currencyDTO.getTimestamp();
    Map<String, BigDecimal> rates = currencyDTO.getRates();

    for (Map.Entry<String, BigDecimal> entry : rates.entrySet()) {
      String currencyCode = entry.getKey();
      BigDecimal price = entry.getValue();

      // Buscar el registro existente por el nombre de la moneda (currencyCode)
      CurrencyEntity byCurrency = currencyRepository.findByCurrency(currencyCode);

      if (byCurrency != null) {
        // Si existe, actualizamos los campos necesarios
        byCurrency.setPrice(price);
        byCurrency.setCalledTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()));
        currencyRepository.save(byCurrency);
      } else {
        // Si no existe, creamos un nuevo registro
        CurrencyEntity currencyEntity = CurrencyEntity.builder()
            .currency(currencyCode)
            .price(price)
            .calledTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()))
            .build();
        currencyRepository.save(currencyEntity);
      }
    }
  }

  /**
   * Metodo para verificar si ya paso una hora
   *  en comparacion con la ultima hora de actualizacion de la tabla currency_data
   */
  public void updateCurrencyDataIfNeeded() {
    // Obtener la hora del primer registro en la tabla
    LocalDateTime firstRecordTime = currencyRepository.findFirstCalledTime();

    if (firstRecordTime == null || shouldUpdateData(firstRecordTime)) {

      log.info(" -- -- Actualizando currency_data -- -- ");
      // Llamar a la API para obtener los datos de las divisas
      CurrencyDTO currencyDTO = getCurrencyRates();

      // Guardar los datos en la tabla currency_data
      saveCurrencyData(currencyDTO);
    }
  }

  /**
   * Metodo para verificar si ya paso una hora basados en el momento actual
   * @param lastUpdate LocalDateTime
   * @return boolean
   */
  private boolean shouldUpdateData(LocalDateTime lastUpdate) {
    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(lastUpdate, now);
    long hoursPassed = duration.toHours();
    return hoursPassed >= 1; // Actualizar si ha pasado al menos 1 hora
  }
}

