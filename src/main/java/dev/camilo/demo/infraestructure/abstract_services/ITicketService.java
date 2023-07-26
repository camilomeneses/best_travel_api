package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.TicketResponse;

import java.util.UUID;
/*interfaz concreta*/
public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{
}
