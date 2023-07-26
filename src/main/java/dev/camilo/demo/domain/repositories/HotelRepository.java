package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/*cuando los elementos se listan en el cliente se usa JpaRepository, sino con
* CrudRepository es suficiente*/
public interface HotelRepository extends JpaRepository<Hotel,Long> {

  /*Querys personalizados con method names*/
  Set<Hotel> findByPriceLessThan(BigDecimal price);
  Set<Hotel> findByPriceBetween(BigDecimal min,BigDecimal max);
  Set<Hotel> findByRatingGreaterThan(Integer rating);
  /*JPQL join with method names*/
  Optional<Hotel> findByReservationsId(UUID id);
}
