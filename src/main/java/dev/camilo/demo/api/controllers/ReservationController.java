package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.HotelPriceResponse;
import dev.camilo.demo.api.models.responses.ReservationResponse;
import dev.camilo.demo.infraestructure.abstract_services.IReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * Controller REST para respuestas JSON y XML para reservation
 */
@Tag(name = "Reservation")
@RestController
@RequestMapping(path = "reservation")
@RequiredArgsConstructor
public class ReservationController {

  //services inyectados
  private final IReservationService reservationService;

  //crear una reservation JSON y XML

  /**
   * Accion POST para crear una nueva reservation
   *
   * @param contentType Header
   * @param request     ReservationRequest
   * @return ResponseEntity
   */
  @Operation(
      summary = "crear una reservation JSON y XML",
      description = "Accion POST para crear una nueva reservation"
  )
  @PostMapping
  public ResponseEntity<ReservationResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @Valid @RequestBody ReservationRequest request) {
    if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON).body(reservationService.create(request));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML).body(reservationService.create(request));
    }
    return ResponseEntity.notFound().build();
  }

  //leer una reservation JSON

  /**
   * Accion GET para obtener una reservation en
   * formato JSON
   *
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "leer una reservation JSON",
      description = "Accion GET para obtener una reservation en formato JSON"
  )
  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReservationResponse> getJson(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(reservationService.read(id));
  }

  //leer una reservation XML

  /**
   * Accion GET para obtener una reservation en formato XML
   *
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "leer una reservation XML",
      description = "Accion GET para obtener una reservation en formato XML"
  )
  @GetMapping(path = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<ReservationResponse> getXml(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML)
        .body(reservationService.read(id));
  }

  //actualizar reservation JSON y XML

  /**
   * Accion PUT para actualizar una reservation
   *
   * @param contentType Header
   * @param id          UUID
   * @param request     ReservationRequest
   * @return ResponseEntity
   */
  @Operation(
      summary = "actualizar reservation JSON y XML",
      description = "Accion PUT para actualizar una reservation"
  )
  @PutMapping(path = "{id}")
  public ResponseEntity<ReservationResponse> put(
      @RequestHeader("Content-Type") String contentType,
      @PathVariable UUID id,
      @Valid @RequestBody ReservationRequest request
  ) {
    if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(reservationService.update(request, id));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML)
          .body(reservationService.update(request, id));
    }
    return ResponseEntity.notFound().build();
  }

  //eliminar reservation JSON y XML

  /**
   * Accion DELETE para eliminar una reservation
   *
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "eliminar reservation JSON y XML",
      description = "Accion DELETE para eliminar una reservation"
  )
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }

  //obtener precio de hotel para reservation JSON

  /**
   * Accion GET para obtener el precio de una reservation segun
   * el precio del hotel a reservar, respuesta en formato JSON
   *
   * @param hotelId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener precio de hotel para reservation JSON",
      description = "Accion GET para obtener el precio de una reservation segun\n" +
          "el precio del hotel a reservar, respuesta en formato JSON"
  )
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HotelPriceResponse> getHotelPriceJson(
      @RequestParam Long hotelId,
      HttpServletRequest request
      ) {
    return getHotelPrice(hotelId, request.getLocale());
  }

  //obtener precio de hotel para reservation XML

  /**
   * Accion GET para obtener el precio de una reservation segun
   * el precio del hotel a reservar, respuesta en formato XML
   *
   * @param hotelId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener precio de hotel para reservation XML",
      description = "Accion GET para obtener el precio de una reservation segun\n" +
          "   el precio del hotel a reservar, respuesta en formato XML"
  )
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<HotelPriceResponse> getHotelPriceXml(
      @RequestParam Long hotelId,
      HttpServletRequest request
  ) {
    return getHotelPrice(hotelId, request.getLocale());
  }


  //private methods

  /**
   * Metodo para traer el precio del hotel de la reservation realizada segun la moneda
   * local del customer
   *
   * @param hotelId Long
   * @return ResponseEntity
   */
  private ResponseEntity<HotelPriceResponse> getHotelPrice(Long hotelId, Locale customerLocale) {
    BigDecimal hotelPrice = reservationService.findPrice(hotelId, customerLocale);

    /*Crear una clase HotelPriceResponse para representar la respuesta en JSON y XML*/
    HotelPriceResponse response = new HotelPriceResponse(hotelPrice);
    return ResponseEntity.ok(response);
  }
}
