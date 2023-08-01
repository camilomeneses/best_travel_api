package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * Interfaz concreta
 */
public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{

  /**
   * obtener precio de un hotel
   * @param hotelId Long
   * @return BigDecimal
   */
  BigDecimal findPrice(Long hotelId, Locale customerLocale);
}
