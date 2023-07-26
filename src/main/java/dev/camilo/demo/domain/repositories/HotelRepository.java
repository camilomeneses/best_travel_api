package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/*cuando los elementos se listan en el cliente se usa JpaRepository, sino con
* CrudRepository es suficiente*/
public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
