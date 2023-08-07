package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.entities.jpa.CurrencyEntity;
import dev.camilo.demo.domain.repositories.jpa.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;

/**
 * Helper para servir el metodo de llamado a el CurrencyRepository
 * para buscar el precio segun el codigo local del usuario (COP,MXN...)
 */
@Component
@RequiredArgsConstructor
public class CurrencyHelper {

  //repositorio inyectado
  private final CurrencyRepository currencyRepository;

  /**
   * Metodo para obtener el precio de algun vuelo, hotel segun la moneda local
   * del cliente
   * @param customerLocale Local
   * @return CurrencyEntity
   */
  public CurrencyEntity getCurrencyEntity(Locale customerLocale) {
    /*Obtener tipo de divisa local del usuario*/
    Currency customerCurrency = Currency.getInstance(customerLocale);
    var customerCurrencyCode = customerCurrency.getCurrencyCode();
    /*traer valor de la divisa actualizada de la tabla currency_data*/
    return currencyRepository.findByCurrency(customerCurrencyCode);
  }
}
