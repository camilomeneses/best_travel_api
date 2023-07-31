package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/*cuando los elementos se listan en el cliente se usa JpaRepository, sino con
* CrudRepository es suficiente*/

/**
 * Acceso a datos de la tabla hotel
 */
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {

  /*Querys personalizados con method names*/

  /**
   * Obtener hoteles por debajo del precio
   * @param price BigDecimal
   * @return Set de HotelEntity
   */
  Set<HotelEntity> findByPriceLessThan(BigDecimal price);

  /**
   * Obtener hoteles entre los precios
   * @param min BigDecimal
   * @param max BigDecimal
   * @return Set de HotelEntity
   */
  Set<HotelEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

  /**
   * Obtener hoteles por encima de rating
   * @param rating Integer
   * @return Set de HotelEntity
   */
  Set<HotelEntity> findByRatingGreaterThan(Integer rating);
  /*JPQL join with method names*/

  /**
   * Obtener hotel por id de reservation
   * @param id UUID
   * @return HotelEntity
   */
  Optional<HotelEntity> findByReservationsId(UUID id);
}
