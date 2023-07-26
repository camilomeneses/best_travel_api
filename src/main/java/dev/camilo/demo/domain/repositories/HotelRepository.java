package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Set;

/*cuando los elementos se listan en el cliente se usa JpaRepository, sino con
* CrudRepository es suficiente*/
public interface HotelRepository extends JpaRepository<Hotel,Long> {

  /*Querys personalizados con method names*/
  Set<Hotel> findByPriceLessThan(BigDecimal price);
  Set<Hotel> findByPriceBetween(BigDecimal min,BigDecimal max);
  Set<Hotel> findByRatingGreaterThan(Integer rating);
}
