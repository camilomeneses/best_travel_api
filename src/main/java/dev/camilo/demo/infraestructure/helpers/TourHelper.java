package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.entities.jpa.*;
import dev.camilo.demo.domain.repositories.jpa.ReservationRepository;
import dev.camilo.demo.domain.repositories.jpa.TicketRepository;
import dev.camilo.demo.infraestructure.services.ReservationService;
import dev.camilo.demo.infraestructure.services.TicketService;
import dev.camilo.demo.util.BestTravelUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Helper para agregar, actualizar y eliminar varios vuelos y hoteles de los tours
 */
@Transactional
@Component
@RequiredArgsConstructor
public class TourHelper {

  //respositorios inyectados
  private final TicketRepository ticketRepository;
  private final ReservationRepository reservationRepository;

  //tratamiento de tickets

  /**
   * Metodo para crear un ticket basado en un vuelo y customer determinados
   *
   * @param fly      FLyEntity
   * @param customer CustomerEntity
   * @return TicketEntity
   */
  public TicketEntity createTicket(FlyEntity fly, CustomerEntity customer) {
    /*persistir en la base de datos*/
    var ticketToPersist = TicketEntity.builder()
        .id(UUID.randomUUID())
        .fly(fly)
        .customer(customer)
        /*aumentar el valor del precio un 25%*/
        .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
        .purchaseDate(LocalDateTime.now())
        .departureDate(BestTravelUtil.getRandomSoon())
        .arrivalDate(BestTravelUtil.getRandomLatter())
        .build();
    return this.ticketRepository.save(ticketToPersist);
  }

  /**
   * Creacion de varios tickets recibimos la lista de vuelos y el cliente
   *
   * @param flights  Set
   * @param customer CustomerEntity
   * @return Set
   */
  public Set<TicketEntity> createTickets(Set<FlyEntity> flights, CustomerEntity customer) {
    var response = new HashSet<TicketEntity>(flights.size());
    flights.forEach(fly -> {
      var ticketToPersist = TicketEntity.builder()
          .id(UUID.randomUUID())
          .fly(fly)
          .customer(customer)
          /*aumentar el valor del precio un 25%*/
          .price(fly.getPrice().add(fly.getPrice().multiply(TicketService.CHARGES_PRICE_PERCENTAGE)))
          .purchaseDate(LocalDateTime.now())
          .departureDate(BestTravelUtil.getRandomSoon())
          .arrivalDate(BestTravelUtil.getRandomLatter())
          .build();

      response.add(this.ticketRepository.save(ticketToPersist));
    });
    return response;
  }

  //tratamiento de reservations

  /**
   * Metodo para crear una reservation
   * @param hotel HotelEntity
   * @param customer CustomerEntity
   * @param totalDays Integer
   * @return ReservationEntity
   */
  public ReservationEntity createReservation(
      HotelEntity hotel,
      CustomerEntity customer,
      Integer totalDays) {
    /*persistir en la base de datos*/
    var reservationToPersist = ReservationEntity.builder()
        .id(UUID.randomUUID())
        .hotel(hotel)
        .customer(customer)
        .totalDays(totalDays)
        .dateTimeReservation(LocalDateTime.now())
        .dateStart(LocalDate.now())
        .dateEnd(LocalDate.now().plusDays(totalDays))
        /*aumentar el valor del precio un 20%*/
        .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
        .build();

    return this.reservationRepository.save(reservationToPersist);
  }

  /**
   * Creacion de reservations, recibimos un HashMap de cada hotel y su numero de dias a reservar, y la informacion
   * del customer
   *
   * @param hotelsByDays HashMap
   * @param customer     CustomerEntity
   * @return Set
   */
  public Set<ReservationEntity> createReservations(HashMap<HotelEntity, Integer> hotelsByDays, CustomerEntity customer) {
    var response = new HashSet<ReservationEntity>(hotelsByDays.size());
    hotelsByDays.forEach((hotel, totalDays) -> {
      var reservationToPersist = ReservationEntity.builder()
          .id(UUID.randomUUID())
          .hotel(hotel)
          .customer(customer)
          .totalDays(totalDays)
          .dateTimeReservation(LocalDateTime.now())
          .dateStart(LocalDate.now())
          .dateEnd(LocalDate.now().plusDays(totalDays))
          /*aumentar el valor del precio un 20%*/
          .price(hotel.getPrice().add(hotel.getPrice().multiply(ReservationService.CHARGES_PRICE_PERCENTAGE)))
          .build();

      response.add(this.reservationRepository.save(reservationToPersist));
    });
    return response;
  }


}
