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
   * @param ticketId
   * @param tourId
   */
  void removeTicket(UUID ticketId, Long tourId);

  /**
   * agregar vuelo de ticket al tour
   * @param flyId
   * @param tourId
   * @return
   */
  UUID addTicket(Long flyId, Long tourId);

  /**
   * eliminar una reservation del tour
   * @param reservationId
   * @param tourId
   */
  void removeReservation(UUID reservationId, Long tourId);

  /**
   * agregar un hotel de reservation al tour
   * @param hotelId
   * @param tourId
   * @return
   */
  UUID addReservation(Long hotelId, Long tourId);
}
