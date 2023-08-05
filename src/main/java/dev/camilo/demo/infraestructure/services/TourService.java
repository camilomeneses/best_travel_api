package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TourRequest;
import dev.camilo.demo.api.models.responses.TourResponse;
import dev.camilo.demo.domain.entities.jpa.*;
import dev.camilo.demo.domain.repositories.jpa.CustomerRepository;
import dev.camilo.demo.domain.repositories.jpa.FlyRepository;
import dev.camilo.demo.domain.repositories.jpa.HotelRepository;
import dev.camilo.demo.domain.repositories.jpa.TourRepository;
import dev.camilo.demo.infraestructure.abstract_services.ITourService;
import dev.camilo.demo.infraestructure.helpers.BlackListHelper;
import dev.camilo.demo.infraestructure.helpers.CustomerHelper;
import dev.camilo.demo.infraestructure.helpers.EmailHelper;
import dev.camilo.demo.infraestructure.helpers.TourHelper;
import dev.camilo.demo.util.enums.Tables;
import dev.camilo.demo.util.exceptions.IdNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service para TourController
 */
@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class TourService implements ITourService {

  //repositorios inyectados
  private final TourRepository tourRepository;
  private final FlyRepository flyRepository;
  private final HotelRepository hotelRepository;
  private final CustomerRepository customerRepository;

  //componentes inyectados
  private final TourHelper tourHelper;
  private  final CustomerHelper customerHelper;
  private final BlackListHelper blackListHelper;
  private final EmailHelper emailHelper;

  //crear tour
  /**
   * Metodo para crear un tour, recibe en el request un HashSet con los ids
   * de los vuelos, y un HashMap con los ids de los hoteles con los dias de reservacion,
   * luego los registra el tour con los debidos ids y customer y retorna un DTO Reponse de
   * TourResponse
   * @param request TourRequest
   * @return TourResponse
   */
  @Override
  public TourResponse create(TourRequest request) {
    this.blackListHelper.isInBlackListCustomer(request.getCustomerId());
    var customer = customerRepository
        .findById(request.getCustomerId())
        .orElseThrow(() -> new IdNotFoundException(Tables.customer.name()));
    var flights = new HashSet<FlyEntity>();
    /*buscando y agregando todos los vuelos de la DB al HashSet de flights*/
    request.getFlights()
        .forEach(fly -> flights
            .add(this.flyRepository
                .findById(fly.getId()).orElseThrow(() -> new IdNotFoundException(Tables.fly.name()))));

    var hotels = new HashMap<HotelEntity, Integer>();
    /*buscando y agregando todos los hoteles de la DB al HashMap de hotels*/
    request.getHotels()
        .forEach(hotel -> hotels
            .put(this.hotelRepository
                .findById(hotel.getId())
                .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name())), hotel.getTotalDays()));

    /*construyendo objeto TourEntity para persistir*/
    var tourToSave = TourEntity.builder()
        .tickets(this.tourHelper.createTickets(flights, customer))
        .reservations(this.tourHelper.createReservations(hotels,customer))
        .customer(customer)
        .build();

    /*persistiendo objeto*/
    var tourSaved = this.tourRepository.save(tourToSave);

    /*incrementar contador de tour para customer*/
    this.customerHelper.increase(customer.getDni(), TourService.class);
    /*incremetar contador de vuelos por este tour*/
    for(int i = 0; i < flights.size();i++){
      this.customerHelper.increase(customer.getDni(), TicketService.class);
    }
    /*incrementar contador de reservations por este tour*/
    for(int i = 0; i < hotels.size(); i++){
      this.customerHelper.increase(customer.getDni(), ReservationService.class);
    }

    /*verificar si viene un email en la request, si viene entonces enviar mail de reservation
     * exitosa*/
    if(Objects.nonNull(request.getEmail()))
      this.emailHelper.sendMail(request.getEmail(),customer.getFullName(),Tables.tour.name());

    /*construyendo y retornando DTO TourResponse*/
    return TourResponse.builder()
        .reservationIds(tourSaved.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
        .ticketsIds(tourSaved.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
        .id(tourSaved.getId())
        .build();
  }

  //obtener tour por id
  /**
   * Metodo para obtener un tour por su id
   * @param id Long
   * @return TourResponse
   */
  @Override
  public TourResponse read(Long id) {
    var tourFromDB = this.tourRepository
        .findById(id)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    return TourResponse.builder()
        .reservationIds(tourFromDB.getReservations().stream().map(ReservationEntity::getId).collect(Collectors.toSet()))
        .ticketsIds(tourFromDB.getTickets().stream().map(TicketEntity::getId).collect(Collectors.toSet()))
        .id(tourFromDB.getId())
        .build();
  }

  //eliminar tour por id
  /**
   * Eliminar un tour por su id
   * @param id Long
   */
  @Override
  public void delete(Long id) {
    var tourToDelete = this.tourRepository
        .findById(id)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    /*reducir contador de tour en customer*/
    this.customerHelper.decrease(tourToDelete.getCustomer().getDni(), TourService.class);
    this.tourRepository.deleteById(id);
  }

  //Tratamiendo de tickets
  /*agregar ticket a tour*/
  /**
   * Metodo para agregar ticket a tour
   * @param flyId Long
   * @param tourId Long
   * @return UUID
   */
  @Override
  public UUID addTicket(Long tourId,Long flyId) {
    /*traer tour para actualizar*/
    var tourToUpdate = this.tourRepository
        .findById(tourId)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    /*traer vuelo*/
    var fly = this.flyRepository
        .findById(flyId)
        .orElseThrow(() -> new IdNotFoundException(Tables.fly.name()));
    /*crear el ticket nuevo*/
    var ticket = this.tourHelper.createTicket(fly,tourToUpdate.getCustomer());
    /*agregar el ticker al tour*/
    tourToUpdate.addTicket(ticket);
    /*actualizar el tour con el nuevo ticket*/
    this.tourRepository.save(tourToUpdate);
    /*devolver el UUID del nuevo ticket agregado*/
    return ticket.getId();
  }

  /*eliminar ticket de tour*/
  /**
   * Metodo para eliminar un ticket de un tour
   * @param ticketId UUID
   * @param tourId Long
   */
  @Override
  public void removeTicket(Long tourId,UUID ticketId ) {
    var tourToUpdate = this.tourRepository
        .findById(tourId)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    /*eliminamos el ticket*/
    tourToUpdate.removeTicket(ticketId);
    /*actualizamos los tickets en el tour*/
    this.tourRepository.save(tourToUpdate);
  }

  //Tratamiendo de reservations
  /**
   * Metodo para crear una reservation e incluirla en el tour
   * @param tourId Long
   * @param hotelId Long
   * @param totalDays Integer
   * @return UUID
   */
  @Override
  public UUID addReservation(Long tourId,Long hotelId, Integer totalDays ) {
    /*traer tour para actualizar*/
    var tourToUpdate = this.tourRepository
        .findById(tourId)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    /*traer hotel*/
    var hotel = this.hotelRepository
        .findById(hotelId)
        .orElseThrow(() -> new IdNotFoundException(Tables.hotel.name()));
    /*hacemos reservation*/
    var reservation = this.tourHelper.createReservation(hotel, tourToUpdate.getCustomer(),totalDays);
    /*actualizamos el tour con la nueva reservation*/
    tourToUpdate.addReservation(reservation);
    /*persistimos*/
    this.tourRepository.save(tourToUpdate);
    return reservation.getId();
  }

  /*eliminar reservation de tour*/
  /**
   * Metodo para eliminar una reservation de un tour
   * @param tourId Long
   * @param reservationId UUID
   */
  @Override
  public void removeReservation(Long tourId,UUID reservationId ) {
    var tourToUpdate = this.tourRepository
        .findById(tourId)
        .orElseThrow(() -> new IdNotFoundException(Tables.tour.name()));
    /*eliminamos el ticket*/
    tourToUpdate.removeReservation(reservationId);
    /*actualizamos los tickets en el tour*/
    this.tourRepository.save(tourToUpdate);
  }

}
