package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface TicketRepository extends CrudRepository<Ticket, UUID> {
}
