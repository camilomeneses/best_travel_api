package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.domain.entities.TicketEntity;
import dev.camilo.demo.domain.repositories.CustomerRepository;
import dev.camilo.demo.domain.repositories.FlyRepository;
import dev.camilo.demo.domain.repositories.TicketRepository;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import dev.camilo.demo.infraestructure.helpers.CustomerHelper;
import dev.camilo.demo.util.BestTravelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Service para TicketController
 */
@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService implements ITicketService {

  //repositorios inyectados
  private final FlyRepository flyRepository;
  private final CustomerRepository customerRepository;
  private final TicketRepository ticketRepository;

  //componente inyectado
  private final CustomerHelper customerHelper;

  //crear nuevo ticket
  /**
   * Metodo para crear un nuevo ticket
   * @param request TicketRequest
   * @return TicketResponse
   */
  @Override
  public TicketResponse create(TicketRequest request) {
    /*variables de entrada del request*/
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

    /*persistir en la base de datos*/
    var ticketToPersist = TicketEntity.builder()
        .id(UUID.randomUUID())
        .fly(fly)
        .customer(customer)
        /*aumentar el valor del precio un 25%*/
        .price(fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
        .purchaseDate(LocalDate.now())
        .departureDate(BestTravelUtil.getRandomSoon())
        .arrivalDate(BestTravelUtil.getRandomLatter())
        .build();

        /*persistir el ticket*/
    var ticketPersisted = this.ticketRepository.save(ticketToPersist);
    log.info("Ticket saved with id: {}",ticketPersisted.getId());

    /*incrementar contador de flights para customer*/
    this.customerHelper.increase(customer.getDni(), TicketService.class);

    return this.entityToResponse(ticketPersisted);
  }

  //obtener un ticket
  /**
   * Metodo para obtener un ticket
   * @param id UUID
   * @return TicketResponse
   */
  @Override
  public TicketResponse read(UUID id) {
    /*variables de entrada del request*/
    var ticketFromDB = this.ticketRepository.findById(id).orElseThrow();
    return this.entityToResponse(ticketFromDB);
  }

  //actualizar ticket
  /**
   * Metodo para actualiza un ticket
   * @param request TicketRequest
   * @param id UUID
   * @return TicketResponse
   */
  @Override
  public TicketResponse update(TicketRequest request, UUID id) {
    /*variables de entrada del request*/
    var ticketToUpdate = ticketRepository.findById(id).orElseThrow();
    var fly = flyRepository.findById(request.getIdFly()).orElseThrow();

    /*actualizacion de valores en el ticket*/
    /*seteo de fly en en ticket*/
    ticketToUpdate.setFly(fly);
    /*aumentar el valor del precio un 25%*/
    ticketToUpdate.setPrice(fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));
    ticketToUpdate.setDepartureDate(BestTravelUtil.getRandomSoon());
    ticketToUpdate.setArrivalDate(BestTravelUtil.getRandomLatter());

    var ticketUpdated = this.ticketRepository.save(ticketToUpdate);
    log.info("Ticket updated with id {}", ticketUpdated.getId());
    return this.entityToResponse(ticketUpdated);
  }

  //eliminar un ticket
  /**
   * Metodo para eliminar un ticket
   * @param id UUID
   */
  @Override
  public void delete(UUID id) {
    /*variables de entrada del request*/
    var ticketToDelete = ticketRepository.findById(id).orElseThrow();
    this.ticketRepository.delete(ticketToDelete);
  }

  //cotizar precio de vuelo
  /**
   * Metodo para devolver el precio del vuelo, tiene
   * agregado un 25% del valor base del vuelo
   * @param flyId Long
   * @return BigDecimal
   */
  @Override
  public BigDecimal findPrice(Long flyId) {
    var fly = this.flyRepository.findById(flyId).orElseThrow();
    return fly.getPrice().add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
  }

  //mapeo de Entity a DTOResponse

  /**
   * Metodo para mapear y convertir una entidad en el DTO response
   * @param entity Ticket
   * @return TicketResponse
   */
  private TicketResponse entityToResponse(TicketEntity entity){
    var response = new TicketResponse();
    BeanUtils.copyProperties(entity,response);
    var flyResponse = new FlyResponse();
    BeanUtils.copyProperties(entity.getFly(),flyResponse);
    response.setFly(flyResponse);
    return response;
  }

  //constantes
  /**
   * Adicional por vuelo
   */
  public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.25);
}
