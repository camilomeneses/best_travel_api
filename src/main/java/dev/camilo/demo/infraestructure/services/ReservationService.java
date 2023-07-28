package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.HotelResponse;
import dev.camilo.demo.api.models.responses.ReservationResponse;
import dev.camilo.demo.domain.entities.ReservationEntity;
import dev.camilo.demo.domain.repositories.CustomerRepository;
import dev.camilo.demo.domain.repositories.HotelRepository;
import dev.camilo.demo.domain.repositories.ReservationRepository;
import dev.camilo.demo.infraestructure.abstract_services.IReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service para ReservationController
 */
@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

  //repositorios inyectados
  private final ReservationRepository reservationRepository;
  private final CustomerRepository customerRepository;
  private final HotelRepository hotelRepository;

  //crear nueva reservation
  /**
   * Metodo para crear una nueva reservation
   * @param request ReservationRequest
   * @return ReservationResponse
   */
  @Override
  public ReservationResponse create(ReservationRequest request) {
    /*variables de entrada del request*/
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

    /*persistir en la base de datos*/
    var reservationToPersist = ReservationEntity.builder()
        .id(UUID.randomUUID())
        .hotelEntity(hotel)
        .customerEntity(customer)
        .totalDays(request.getTotalDays())
        .dateTimeReservation(LocalDateTime.now())
        .dateStart(LocalDate.now())
        .dateEnd(LocalDate.now().plusDays(request.getTotalDays()))
        /*aumentar el valor del precio un 20%*/
        .price(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)))
        .build();

    var reservationPersisted = reservationRepository.save(reservationToPersist);
    return this.entityToResponse(reservationPersisted);
  }

  //obtener una reservation

  /**
   * Metodo para obtener una reservation
   * @param id UUID
   * @return ReservationResponse
   */
  @Override
  public ReservationResponse read(UUID id) {
    /*variables de entrada del request*/
    var reservationFromDB = reservationRepository.findById(id).orElseThrow();
    return this.entityToResponse(reservationFromDB);
  }

  //actualizar reservation
  /**
   * Metodo para actualizar una reservation
   * @param request ReservationRequest
   * @param id UUID
   * @return ReservationResponse
   */
  @Override
  public ReservationResponse update(ReservationRequest request, UUID id) {
    /*variables de entrada del request*/
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();

    /*actualizacion de valores en reservation*/
    var reservationToUpdate = reservationRepository.findById(id).orElseThrow();

    /*seteo de hotel en reservation*/
    reservationToUpdate.setHotelEntity(hotel);
    reservationToUpdate.setTotalDays(request.getTotalDays());
    reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
    reservationToUpdate.setDateStart(LocalDate.now());
    reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
    reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));

    var reservationUpdated = reservationRepository.save(reservationToUpdate);
    log.info("Reservation updated with id {}", reservationUpdated.getId());
    return this.entityToResponse(reservationUpdated);
  }

  //eliminar una reservation
  /**
   * Metodo para eliminar una reservation
   * @param id UUID
   */
  @Override
  public void delete(UUID id) {
    /*variables de entrada del request*/
    var reservationToDelete = reservationRepository.findById(id).orElseThrow();
    this.reservationRepository.delete(reservationToDelete);
  }

  //cotizar precio de reservacion
  /**
   * Metodo para devolver el precio de la reservation, tiene
   * agregado un 20% del valor base del hotel
   * @param hotelId Long
   * @return BigDecimal
   */
  @Override
  public BigDecimal findPrice(Long hotelId) {
    var hotel = hotelRepository.findById(hotelId).orElseThrow();
    return hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
  }

  //mapeo de Entity a DTOResponse
  /**
   * Metodo para mapear y convertir una entidad en el DTO response
   * @param entity Reservation
   * @return ReservationResponse
   */
  private ReservationResponse entityToResponse(ReservationEntity entity) {
    var response = new ReservationResponse();
    BeanUtils.copyProperties(entity, response);
    var hotelResponse = new HotelResponse();
    BeanUtils.copyProperties(entity.getHotelEntity(), hotelResponse);
    response.setHotel(hotelResponse);
    return response;
  }

  // constantes
  /**
   * Adicional por reservation
   */
  public static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);

}
