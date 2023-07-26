package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Fly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface FlyRepository extends JpaRepository<Fly, Long> {
  /*consultas JPQL (default)*/
  @Query("select f from fly f where f.price < :price")
  Set<Fly> selectLessPrice(BigDecimal price);

  @Query("select f from fly f where f.price between :min and :max")
  Set<Fly> selectBetweenPrice(BigDecimal min,BigDecimal max);

  @Query("select f from fly f where f.originName = :origin and f.destinyName = :destiny")
  Set<Fly> selectOriginDestiny(String origin, String destiny);

  /*JOIN JPQL*/

  @Query("select f from fly f left join fetch f.tickets t where t.id = :id")
  Optional<Fly> findByTicketId(UUID id);
}
