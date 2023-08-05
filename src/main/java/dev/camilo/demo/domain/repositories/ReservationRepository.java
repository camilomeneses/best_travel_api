package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.jpa.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

/**
 * Acceso a datos en la tabla reservation
 */
public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
