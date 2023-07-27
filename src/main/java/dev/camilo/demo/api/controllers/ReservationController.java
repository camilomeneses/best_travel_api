package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.ReservationRequest;
import dev.camilo.demo.api.models.responses.ReservationResponse;
import dev.camilo.demo.infraestructure.abstract_services.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "reservation")
@RequiredArgsConstructor
public class ReservationController {

  private final IReservationService reservationService;

  /*crear una reservacion JSON y XML*/
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
}
