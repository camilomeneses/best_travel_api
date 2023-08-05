package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.domain.entities.jpa.CurrencyEntity;
import dev.camilo.demo.domain.entities.jpa.TicketEntity;
import dev.camilo.demo.domain.repositories.jpa.CustomerRepository;
import dev.camilo.demo.domain.repositories.jpa.FlyRepository;
import dev.camilo.demo.domain.repositories.jpa.TicketRepository;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import dev.camilo.demo.infraestructure.helpers.BlackListHelper;
import dev.camilo.demo.infraestructure.helpers.CurrencyHelper;
import dev.camilo.demo.infraestructure.helpers.CustomerHelper;
import dev.camilo.demo.infraestructure.helpers.EmailHelper;
import dev.camilo.demo.util.BestTravelUtil;
import dev.camilo.demo.util.enums.Tables;
import dev.camilo.demo.util.exceptions.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
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

  //componentes inyectados
  private final CustomerHelper customerHelper;
  private final BlackListHelper blackListHelper;
  private final CurrencyHelper currencyHelper;
  private final EmailHelper emailHelper;

  //crear nuevo ticket
  /**
   * Metodo para crear un nuevo ticket
   * @param request TicketRequest
   * @return TicketResponse
   */
  @Override
  public TicketResponse create(TicketRequest request) {
    this.blackListHelper.isInBlackListCustomer(request.getIdClient());
    /*variables de entrada del request*/
    var fly = flyRepository
        .findById(request.getIdFly())
        .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
    var customer = customerRepository
        .findById(request.getIdClient())
        .orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));

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

    /*verificar si viene un email en la request, si viene entonces enviar mail de reservation
     * exitosa*/
    if(Objects.nonNull(request.getEmail()))
      this.emailHelper.sendMail(request.getEmail(),customer.getFullName(),Tables.ticket.name());

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
    var ticketFromDB = this.ticketRepository
        .findById(id)
        .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
    return this.entityToResponse(ticketFromDB);
  }

  //actualizar ticket
  /**
   * Metodo para actualiza un ticket
   * @param request TicketRequest
   * @param id UUID
   * @return TicketResponseb
   */
  @Override
  public TicketResponse update(TicketRequest request, UUID id) {
    /*variables de entrada del request*/
    var ticketToUpdate = ticketRepository
        .findById(id)
        .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
    var fly = flyRepository
        .findById(request.getIdFly())
        .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));

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
    var ticketToDelete = ticketRepository
        .findById(id)
        .orElseThrow(() -> new IdNotFoundException(Tables.ticket.name()));
    /*reducir contador de tickets en customer*/
    this.customerHelper.decrease(ticketToDelete.getCustomer().getDni(), TicketService.class);
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
  public BigDecimal findPrice(Long flyId, Locale customerLocale) {
    var fly = this.flyRepository
        .findById(flyId)
        .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
    /*Obtener precio de divisa del usuario*/
    CurrencyEntity customerCurrencyEntity = currencyHelper
        .getCurrencyEntity(customerLocale);
    return fly.getPrice()
        .add(fly.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)) //agregar 25% al valor
        .multiply(customerCurrencyEntity.getPrice()); //convertir a moneda local
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
