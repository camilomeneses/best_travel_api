package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    return ResponseEntity.badRequest().build();
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
    return ResponseEntity.badRequest().build();
  }
}
