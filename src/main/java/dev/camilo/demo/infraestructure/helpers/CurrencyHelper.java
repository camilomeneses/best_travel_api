package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.entities.CurrencyEntity;
import dev.camilo.demo.domain.repositories.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CurrencyHelper {

  private final CurrencyRepository currencyRepository;

  public CurrencyEntity getCurrencyEntity(Locale customerLocale) {
    /*Obtener tipo de divisa local del usuario*/
    Currency customerCurrency = Currency.getInstance(customerLocale);
    var customerCurrencyCode = customerCurrency.getCurrencyCode();
    /*traer valor de la divisa actualizada de la tabla currency_data*/
    return currencyRepository.findByCurrency(customerCurrencyCode);
  }
}
