package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "ticket")
@RequiredArgsConstructor
public class TicketController {

  private final ITicketService ticketService;

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
}
