package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.ReservationEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;


public interface ReservationRepository extends CrudRepository<ReservationEntity, UUID> {
}
