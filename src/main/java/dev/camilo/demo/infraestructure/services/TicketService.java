package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.domain.entities.Ticket;
import dev.camilo.demo.domain.repositories.CustomerRepository;
import dev.camilo.demo.domain.repositories.FlyRepository;
import dev.camilo.demo.domain.repositories.TicketRepository;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService implements ITicketService {

  private final FlyRepository flyRepository;
  private final CustomerRepository customerRepository;
  private final TicketRepository ticketRepository;

  @Override
  public TicketResponse create(TicketRequest request) {
    /*variables de entrada del request*/
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

    /*persistir en la base de datos*/
    var ticketToPersist = Ticket.builder()
        .id(UUID.randomUUID())
        .fly(fly)
        .customer(customer)
        .price(fly.getPrice().multiply(BigDecimal.valueOf(0.25)))
        .purchaseDate(LocalDate.now())
        .arrivalDate(LocalDateTime.now())
        .departureDate(LocalDateTime.now())
        .build();

    var ticketPersisted = this.ticketRepository.save(ticketToPersist);
    log.info("Ticket saved with id: {}",ticketPersisted.getId());
    return this.entityToResponse(ticketPersisted);
  }

  @Override
  public TicketResponse read(UUID id) {
    var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
    return this.entityToResponse(ticketFromDB);
  }

  @Override
  public TicketResponse update(TicketRequest request, UUID id) {
    return null;
  }

  @Override
  public void delete(UUID id) {

  }

  //mapeo de Entity a DTOResponse
  private TicketResponse entityToResponse(Ticket entity){
    var response = new TicketResponse();
    BeanUtils.copyProperties(entity,response);
    var flyResponse = new FlyResponse();
    BeanUtils.copyProperties(entity.getFly(),flyResponse);
    response.setFly(flyResponse);
    return response;
  }
}
