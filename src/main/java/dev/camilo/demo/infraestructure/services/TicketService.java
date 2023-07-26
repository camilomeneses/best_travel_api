package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;

import java.util.UUID;

public class TicketService implements ITicketService {
  @Override
  public TicketResponse create(TicketRequest request) {
    return null;
  }

  @Override
  public TicketResponse read(UUID id) {
    return null;
  }

  @Override
  public TicketResponse update(TicketRequest request, UUID id) {
    return null;
  }

  @Override
  public void delete(UUID id) {

  }
}
