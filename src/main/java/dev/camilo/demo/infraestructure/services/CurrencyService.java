package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.infraestructure.helpers.ApiCurrencyConnectorHelper;
import dev.camilo.demo.util.constants.CurrencyConstanst;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * responsable de mantener actualizados los datos de las tasas de cambio
 * de divisas en la base de datos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {
  private final ApiCurrencyConnectorHelper currencyConnectorHelper;

  /**
   * verificar ultima hora de actualizacion despues de
   * arrancar la aplicacion y actualizar si es necesario
   */
  @PostConstruct
  public void init() {
    log.info(" -- -- verificando ultima hora de actualizacion de currency_data [init]  -- -- ");
    currencyConnectorHelper.updateCurrencyDataIfNeeded();
  }

  /**
   * cada quince minutos verificar si ha pasado una hora de la hora de
   * ultima actualizacion guardada en la tabla currency_date
   */
  ;
  @Scheduled(cron = CurrencyConstanst.SCHEDULED_VERIFICATION_TIME)
  public void updateCurrencyDataHourly() {
    log.info(" -- -- verificando ultima hora de actualizacion de currency_data [cron] -- -- ");
    currencyConnectorHelper.updateCurrencyDataIfNeeded();
  }
}
