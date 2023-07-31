package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * Acceso a datos de la tabla currency_date
 */
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {

  /**
   * funcion para encontrar la ultima fecha de actualizacion de las divisas
   * @return LocaleDateTime
   */
  @Query("SELECT MIN(c.calledTime) FROM CurrencyEntity c")
  LocalDateTime findFirstCalledTime();

  /**
   * funcion para encontrar un registro de divisa por su nombre
   * @param currencyName String
   * @return CurrencyEntity
   */
  CurrencyEntity findByCurrency(String currencyName);
}
