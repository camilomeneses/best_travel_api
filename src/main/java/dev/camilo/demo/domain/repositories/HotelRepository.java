package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/*cuando los elementos se listan en el cliente se usa JpaRepository, sino con
* CrudRepository es suficiente*/
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {

  /*Querys personalizados con method names*/
  Set<HotelEntity> findByPriceLessThan(BigDecimal price);
  Set<HotelEntity> findByPriceBetween(BigDecimal min, BigDecimal max);
  Set<HotelEntity> findByRatingGreaterThan(Integer rating);
  /*JPQL join with method names*/
  Optional<HotelEntity> findByReservationsId(UUID id);
}
