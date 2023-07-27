package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.HotelPriceResponse;
import dev.camilo.demo.api.models.responses.ReservationResponse;
import dev.camilo.demo.infraestructure.abstract_services.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final IReservationService reservationService;

  //crear una reservation JSON y XML
  @PostMapping
  public ResponseEntity<ReservationResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @RequestBody ReservationRequest request) {
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
  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ReservationResponse> getJson(@PathVariable UUID id){
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
        .body(reservationService.read(id));
  }

  //leer una reservation XML
  @GetMapping(path = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<ReservationResponse> getXml(@PathVariable UUID id){
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML)
        .body(reservationService.read(id));
  }

  //actualizar reservation JSON y XML
  @PutMapping(path = "{id}")
  public ResponseEntity<ReservationResponse> put(
      @RequestHeader("Content-Type") String contentType,
      @PathVariable UUID id,
      @RequestBody ReservationRequest request
  ){
    if(MediaType.APPLICATION_JSON_VALUE.equals(contentType)){
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(reservationService.update(request,id));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML)
          .body(reservationService.update(request,id));
    }
    return ResponseEntity.notFound().build();
  }

  //eliminar reservation JSON y XML
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id){
    this.reservationService.delete(id);
    return ResponseEntity.noContent().build();
  }

  //obtener precio de hotel para reservation JSON
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HotelPriceResponse> getHotelPriceJson(
      @RequestParam Long hotelId
  ){
    return getHotelPrice(hotelId);
  }

  //obtener precio de hotel para reservation XML
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<HotelPriceResponse> getHotelPriceXml(
      @RequestParam Long hotelId
  ){
    return getHotelPrice(hotelId);
  }


  private ResponseEntity<HotelPriceResponse> getHotelPrice(Long hotelId) {
    BigDecimal hotelPrice = reservationService.findPrice(hotelId);

    /*Crear una clase HotelPriceResponse para representar la respuesta en JSON y XML*/
    HotelPriceResponse response = new HotelPriceResponse(hotelPrice);
    return ResponseEntity.ok(response);
  }
}
