package dev.camilo.demo.domain.repositories.jpa;

import dev.camilo.demo.domain.entities.jpa.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Acceso a datos en la tabla ticket
 */
public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
