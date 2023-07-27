package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.HotelResponse;
import dev.camilo.demo.api.models.responses.ReservationResponse;
import dev.camilo.demo.domain.entities.Reservation;
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

@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService implements IReservationService {

  private final ReservationRepository reservationRepository;
  private final CustomerRepository customerRepository;
  private final HotelRepository hotelRepository;

  @Override
  public ReservationResponse create(ReservationRequest request) {
    /*variables de entrada del request*/
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();
    var customer = customerRepository.findById(request.getIdClient()).orElseThrow();

    /*persistir en la base de datos*/
    var reservationToPersist = Reservation.builder()
        .id(UUID.randomUUID())
        .hotel(hotel)
        .customer(customer)
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

  @Override
  public ReservationResponse read(UUID id) {
    /*variables de entrada del request*/
    var reservationFromDB = reservationRepository.findById(id).orElseThrow();
    return this.entityToResponse(reservationFromDB);
  }

  @Override
  public ReservationResponse update(ReservationRequest request, UUID id) {
    /*variables de entrada del request*/
    var hotel = hotelRepository.findById(request.getIdHotel()).orElseThrow();

    //actualizacion de valores en reservation
    var reservationToUpdate = reservationRepository.findById(id).orElseThrow();

    /*seteo de hotel en reservation*/
    reservationToUpdate.setHotel(hotel);
    reservationToUpdate.setTotalDays(request.getTotalDays());
    reservationToUpdate.setDateTimeReservation(LocalDateTime.now());
    reservationToUpdate.setDateStart(LocalDate.now());
    reservationToUpdate.setDateEnd(LocalDate.now().plusDays(request.getTotalDays()));
    reservationToUpdate.setPrice(hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE)));

    var reservationUpdated = reservationRepository.save(reservationToUpdate);
    log.info("Reservation updated with id {}", reservationUpdated.getId());
    return this.entityToResponse(reservationUpdated);
  }

  @Override
  public void delete(UUID id) {
    /*variables de entrada del request*/
    var reservationToDelete = reservationRepository.findById(id).orElseThrow();
    this.reservationRepository.delete(reservationToDelete);
  }

  @Override
  public BigDecimal findPrice(Long hotelId) {
    var hotel = hotelRepository.findById(hotelId).orElseThrow();
    return hotel.getPrice().add(hotel.getPrice().multiply(CHARGES_PRICE_PERCENTAGE));
  }

  //mapeo de Entity a DTOResponse
  private ReservationResponse entityToResponse(Reservation entity) {
    var response = new ReservationResponse();
    BeanUtils.copyProperties(entity, response);
    var hotelResponse = new HotelResponse();
    BeanUtils.copyProperties(entity.getHotel(), hotelResponse);
    response.setHotel(hotelResponse);
    return response;
  }

  private static final BigDecimal CHARGES_PRICE_PERCENTAGE = BigDecimal.valueOf(0.20);

}
