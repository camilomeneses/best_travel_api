package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.TourRequest;
import dev.camilo.demo.api.models.responses.TourResponse;
import dev.camilo.demo.domain.entities.*;
import dev.camilo.demo.domain.repositories.CustomerRepository;
import dev.camilo.demo.domain.repositories.FlyRepository;
import dev.camilo.demo.domain.repositories.HotelRepository;
import dev.camilo.demo.domain.repositories.TourRepository;
import dev.camilo.demo.infraestructure.abstract_services.ITourService;
import dev.camilo.demo.infraestructure.helpers.TourHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
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

  //componente inyectado
  private final TourHelper tourHelper;

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
    var customer = customerRepository.findById(request.getCustomerId()).orElseThrow();
    var flights = new HashSet<FlyEntity>();
    /*buscando y agregando todos los vuelos de la DB al HashSet de flights*/
    request.getFlights()
        .forEach(fly -> flights.add(this.flyRepository.findById(fly.getId()).orElseThrow()));

    var hotels = new HashMap<HotelEntity, Integer>();
    /*buscando y agregando todos los hoteles de la DB al HashMap de hotels*/
    request.getHotels()
        .forEach(hotel -> hotels
            .put(this.hotelRepository.findById(hotel.getId()).orElseThrow(), hotel.getTotalDays()));

    /*construyendo objeto TourEntity para persistir*/
    var tourToSave = TourEntity.builder()
        .tickets(this.tourHelper.createTickets(flights, customer))
        .reservations(this.tourHelper.createReservations(hotels,customer))
        .customer(customer)
        .build();

    /*persistiendo objeto*/
    var tourSaved = this.tourRepository.save(tourToSave);

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
    var tourFromDB = this.tourRepository.findById(id).orElseThrow();
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
    var tourToDelete = this.tourRepository.findById(id).orElseThrow();
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
    var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
    /*traer vuelo*/
    var fly = this.flyRepository.findById(flyId).orElseThrow();
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
    var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
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
    var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
    /*traer hotel*/
    var hotel = this.hotelRepository.findById(hotelId).orElseThrow();
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
    var tourToUpdate = this.tourRepository.findById(tourId).orElseThrow();
    /*eliminamos el ticket*/
    tourToUpdate.removeReservation(reservationId);
    /*actualizamos los tickets en el tour*/
    this.tourRepository.save(tourToUpdate);
  }

}
