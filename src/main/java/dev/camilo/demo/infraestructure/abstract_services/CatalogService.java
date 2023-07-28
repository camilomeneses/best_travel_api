package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.util.enums.SortType;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Interfaz generica
 * @param <ResponseObj>
 */
public interface CatalogService <ResponseObj>{
  /**
   * Obtener todos los elementos paginados
   * @param page Integer
   * @param size Integer
   * @param sortType Enum
   * @return Page
   */
  Page<ResponseObj> readAll(Integer page, Integer size, SortType sortType);

  /**
   * Obtener elementos del precio menor
   * @param price BigDecimal
   * @return Set
   */
  Set<ResponseObj> readLessPrice(BigDecimal price);

  /**
   * Obtener elementos entre un precio minimo y uno maximo
   * @param min BigDecimal
   * @param max BigDecimal
   * @return Set
   */
  Set<ResponseObj> readBetweenPrices(BigDecimal min, BigDecimal max);

  //contantes
  String FIEL_BY_SORT = "price";
}
