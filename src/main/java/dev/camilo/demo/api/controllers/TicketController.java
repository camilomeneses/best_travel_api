package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.FlyPriceResponse;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping(path = "ticket")
@RequiredArgsConstructor
public class TicketController {

  private final ITicketService ticketService;

  /*crear un ticket JSON y XML*/
  @PostMapping
  public ResponseEntity<TicketResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @RequestBody TicketRequest request)
  {
    if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON).body(ticketService.create(request));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML).body(ticketService.create(request));
    }
    return ResponseEntity.notFound().build();
  }

  /*leer un ticket JSON*/
  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TicketResponse> getJson(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ticketService.read(id));
  }

  /*leer un ticket XML*/
  @GetMapping(path = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<TicketResponse> getXml(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ticketService.read(id));
  }

  /*actualizar ticket JSON y XML*/
  @PutMapping(path = "{id}")
  public ResponseEntity<TicketResponse> put(
      @RequestHeader("Content-Type") String contentType,
      @PathVariable UUID id,
      @RequestBody TicketRequest request) {
    if (MediaType.APPLICATION_JSON_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(ticketService.update(request, id));
    } else if (MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      return ResponseEntity.ok()
          .contentType(MediaType.APPLICATION_XML)
          .body(ticketService.update(request, id));
    }
    return ResponseEntity.notFound().build();
  }

  /*eliminar ticket JSON y XML*/
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.ticketService.delete(id);
    return ResponseEntity.noContent().build();
  }

  /*obtener precio de vuelo JSON*/
  @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FlyPriceResponse> getFlyPriceJson(
      @RequestParam Long flyId
  ) {
    BigDecimal flyPrice = this.ticketService.findPrice(flyId);

    // Crear una instancia de FlyPriceResponse con el precio del vuelo
    FlyPriceResponse response = new FlyPriceResponse(flyPrice);
    return ResponseEntity.ok(response);
  }

  /*obtener precio de vuelo XML*/
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<FlyPriceResponse> getFlyPriceXml(
      @RequestParam Long flyId
  ) {
    BigDecimal flyPrice = this.ticketService.findPrice(flyId);

    // Crear una clase FlyPriceResponse para representar la respuesta en XML
    FlyPriceResponse response = new FlyPriceResponse();
    response.setFlyPrice(flyPrice);

    return ResponseEntity.ok(response);
  }
}
