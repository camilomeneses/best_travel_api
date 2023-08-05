package dev.camilo.demo.domain.repositories.jpa;

import dev.camilo.demo.domain.entities.jpa.FlyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Acceso a datos a la tabla de fly
 */
public interface FlyRepository extends JpaRepository<FlyEntity, Long> {
  /*consultas JPQL (default)*/

  /**
   * Obtener vuelos por debajo del precio
   * @param price BigDecimal
   * @return Set de FlyEntity
   */
  @Query("select f from fly f where f.price < :price")
  Set<FlyEntity> selectLessPrice(BigDecimal price);

  /**
   * Obtener vuelos entre los precios
   * @param min BigDecimal
   * @param max BigDecimal
   * @return Set de FlyEntity
   */
  @Query("select f from fly f where f.price between :min and :max")
  Set<FlyEntity> selectBetweenPrice(BigDecimal min, BigDecimal max);

  /**
   * Obtener vuelos con match de origen y destino
   * @param origin String
   * @param destiny String
   * @return Set de FlyEntity
   */
  @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
  Set<FlyEntity> selectOriginDestiny(String origin, String destiny);

  /*JOIN JPQL*/

  /**
   * Obtener vuelo que haga match con UUID de ticket
   * @param id UUID
   * @return Optional de FlyEntity
   */
  @Query("select f from fly f left join fetch f.tickets t where t.id = :id")
  Optional<FlyEntity> findByTicketId(UUID id);
}
