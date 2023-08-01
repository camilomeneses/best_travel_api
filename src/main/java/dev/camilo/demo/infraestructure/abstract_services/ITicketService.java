package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.TicketResponse;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * Interfaz concreta
 */
public interface ITicketService extends CrudService<TicketRequest, TicketResponse, UUID>{

  /**
   * obtener el precio de un vuelo
   * @param flyId Long
   * @return BigDecimal
   */
  BigDecimal findPrice(Long flyId, Locale customerLocale);
}
