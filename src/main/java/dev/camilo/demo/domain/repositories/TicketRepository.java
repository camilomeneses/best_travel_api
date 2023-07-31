package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Acceso a datos en la tabla ticket
 */
public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
