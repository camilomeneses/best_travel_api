package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.ReservationResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface IReservationService extends CrudService<ReservationRequest, ReservationResponse, UUID>{

  BigDecimal findPrice(Long hotelId);
}
