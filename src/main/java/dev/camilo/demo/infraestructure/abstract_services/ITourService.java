package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.request.TourRequest;
import dev.camilo.demo.api.models.responses.TourResponse;

import java.util.UUID;

/**
 * Interfaz concreta
 */
public interface ITourService extends SimpleCrudService<TourRequest, TourResponse, Long>{

  /**
   * eliminar un ticket del tour
   * @param tourId Long
   * @param ticketId UUID
   */
  void removeTicket(Long tourId,UUID ticketId);

  /**
   * agregar vuelo de ticket al tour
   * @param tourId Long
   * @param flyId Long
   * @return
   */
  UUID addTicket(Long tourId,Long flyId);

  /**
   * eliminar una reservation del tour
   * @param tourId Long
   * @param reservationId UUID
   */
  void removeReservation(Long tourId,UUID reservationId);

  /**
   * agregar un hotel de reservation al tour con el numero de dias
   * a reservar
   * @param tourId Long
   * @param hotelId Long
   * @param totalDays Integer
   * @return UUID
   */
  UUID addReservation(Long tourId,Long hotelId, Integer totalDays);
}
