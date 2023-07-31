package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TourRequest;
import dev.camilo.demo.api.models.responses.TourReservationResponse;
import dev.camilo.demo.api.models.responses.TourResponse;
import dev.camilo.demo.api.models.responses.TourTicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller REST para respuestas JSON y XML para tour
 */
@Tag(name = "Reservation")
@RestController
@RequestMapping(path = "tour")
@RequiredArgsConstructor
public class TourController {

  //servicios inyectados
  private final ITourService tourService;

  //crear tour JSON y XML
  /**
   * Metodo para crear un tour, regresa un JSON o XML segun
   * el Content-Type del Header
   *
   * @param contentType Header
   * @param request TourRequest
   * @return ResponseEntity
   */
  @Operation(
      summary = "crear tour JSON y XML",
      description = "Metodo para crear un tour, regresa un JSON o XML segun el Content-Type del Header"
  )
  @PostMapping
  public ResponseEntity<TourResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @Valid @RequestBody TourRequest request
  ) {
    if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(this.tourService.create(request));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML)
          .body(this.tourService.create(request));
    }
    return ResponseEntity.notFound().build();
  }

  //obtener tour por id JSON
  /**
   * Obtener tour por su id, response en formato JSON
   * @param id Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener tour por id JSON",
      description = "Obtener tour por su id, response en formato JSON"
  )
  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TourResponse> getJson(@PathVariable Long id) {
    return ResponseEntity.ok(this.tourService.read(id));
  }

  //obtener tour por id XML
  /**
   * Obtener tour por su id, response en formato XML
   * @param id Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener tour por id XML",
      description = "Obtener tour por su id, response en formato XML"
  )
  @GetMapping(path = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<TourResponse> getXml(@PathVariable Long id) {
    return ResponseEntity.ok(this.tourService.read(id));
  }

  //eliminar tour por id
  /**
   * Eliminar tour por id
   * @param id Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "eliminar tour por id",
      description = "Eliminar tour por id"
  )
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id){
    this.tourService.delete(id);
    return ResponseEntity.noContent().build();
  }

  //eliminar ticket de tour
  /**
   * Eliminar ticket de tour
   * @param tourId Long
   * @param ticketId UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "eliminar ticket de tour",
      description = "Eliminar ticket de tour"
  )
  @PatchMapping(path = "{tourId}/remove_ticket/{ticketId}")
  public ResponseEntity<Void> deleteTicket(
      @PathVariable Long tourId,
      @PathVariable UUID ticketId
      ){
    this.tourService.removeTicket(tourId,ticketId);
    return ResponseEntity.noContent().build();
  }

  //agregar ticket a tour JSON
  /**
   * Metodo para agregar el ticket a el tour,
   * response formato JSON
   * @param tourId Long
   * @param flyId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "agregar ticket a tour JSON",
      description = "Metodo para agregar el ticket a el tour, response formato JSON"
  )
  @PatchMapping(path = "{tourId}/add_ticket/{flyId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TourTicketResponse> addTicketJson(
      @PathVariable Long tourId,
      @PathVariable Long flyId
  ){
    var response = new TourTicketResponse(this.tourService.addTicket(tourId,flyId));
    return ResponseEntity.ok(response);
  }

  //agregar ticket a tour XML
  /**
   * Metodo para agregar el ticket a el tour,
   * response formato XML
   * @param tourId Long
   * @param flyId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "agregar ticket a tour XML",
      description = "Metodo para agregar el ticket a el tour, response formato XML"
  )
  @PatchMapping(path = "/xml/{tourId}/add_ticket/{flyId}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<TourTicketResponse> addTicketXml(
      @PathVariable Long tourId,
      @PathVariable Long flyId
  ){
    var response = new TourTicketResponse(this.tourService.addTicket(tourId,flyId));
    return ResponseEntity.ok(response);
  }

  //eliminar reservation de tour
  /**
   * Metodo para eliminar una reservation de un tour
   * @param tourId  Long
   * @param reservationId UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "agregar ticket a tour XML",
      description = "Metodo para eliminar una reservation de un tour"
  )
  @PatchMapping(path = "{tourId}/remove_reservation/{reservationId}")
  public ResponseEntity<Void> deleteReservation(
      @PathVariable Long tourId,
      @PathVariable UUID reservationId
  ){
    this.tourService.removeReservation(tourId,reservationId);
    return ResponseEntity.noContent().build();
  }

  //agregar reservation a tour JSON
  /**
   * Agregar una reservation a tour, response
   * formato JSON
   * @param tourId Long
   * @param hotelId Long
   * @param totalDays Integer
   * @return ResponseEntity
   */
  @Operation(
      summary = "agregar reservation a tour JSON",
      description = "Agregar una reservation a tour, response formato JSON"
  )
  @PatchMapping(path = "{tourId}/add_reservation/{hotelId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TourReservationResponse> addReservationJson(
      @PathVariable Long tourId,
      @PathVariable Long hotelId,
      @RequestParam Integer totalDays
  ){
    var response = new TourReservationResponse(this.tourService.addReservation(tourId,hotelId,totalDays));
    return ResponseEntity.ok(response);
  }

  //agregar reservation a tour XML
  /**
   * Agregar una reservation a tour, response
   * formato XML
   * @param tourId Long
   * @param hotelId Long
   * @param totalDays Integer
   * @return ResponseEntity
   */
  @Operation(
      summary = "agregar reservation a tour XML",
      description = "Agregar una reservation a tour, response formato XML"
  )
  @PatchMapping(path = "/xml/{tourId}/add_reservation/{hotelId}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<TourReservationResponse> addReservationXml(
      @PathVariable Long tourId,
      @PathVariable Long hotelId,
      @RequestParam Integer totalDays
  ){
    var response = new TourReservationResponse(this.tourService.addReservation(tourId,hotelId,totalDays));
    return ResponseEntity.ok(response);
  }
}
