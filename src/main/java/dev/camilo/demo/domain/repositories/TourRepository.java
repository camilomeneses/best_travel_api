package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.jpa.TourEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Acceso a datos en la tabla tour
 */
public interface TourRepository extends CrudRepository<TourEntity,Long> {
}
