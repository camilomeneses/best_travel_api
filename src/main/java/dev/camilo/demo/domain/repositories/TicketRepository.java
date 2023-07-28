package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.TicketEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {
}
