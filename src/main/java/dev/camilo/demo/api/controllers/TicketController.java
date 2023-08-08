package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TicketRequest;
import dev.camilo.demo.api.models.responses.FlyPriceResponse;
import dev.camilo.demo.api.models.responses.TicketResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

/**
 * Controller REST para respuestas JSON y XML para ticket
 */
@Tag(name = "Ticket")
@RestController
@RequestMapping(path = "ticket")
@RequiredArgsConstructor
public class TicketController {

  //services inyectados
  private final ITicketService ticketService;

  //crear un ticket JSON y XML
  /**
   * Accion POST para crear una nuevo ticket
   * @param contentType Header
   * @param request TicketRequest
   * @return ResponseEntity
   */
  @Operation(
      summary = "crear un ticket JSON y XML",
      description = "Accion POST para crear una nuevo ticket"
  )
  @PostMapping
  public ResponseEntity<TicketResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @Valid @RequestBody TicketRequest request)
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

  //leer un ticket JSON
  /**
   * Accion GET para obtener ticket en formato JSON
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "leer un ticket JSON",
      description = "Accion GET para obtener ticket en formato JSON"
  )
  @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TicketResponse> getJson(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(ticketService.read(id));
  }

  //leer un ticket XML
  /**
   * Accion GET para obtener ticket en formato XML
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "leer un ticket XML",
      description = "Accion GET para obtener ticket en formato XML"
  )
  @GetMapping(path = "/xml/{id}", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<TicketResponse> getXml(@PathVariable UUID id) {
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(ticketService.read(id));
  }

  //actualizar ticket JSON y XML
  /**
   * Accion PUT para actualizar un ticket
   * @param contentType Header
   * @param id UUID
   * @param request TicketRequest
   * @return ResponseEntity
   */
  @Operation(
      summary = "actualizar ticket JSON y XML",
      description = "Accion PUT para actualizar un ticket"
  )
  @PutMapping(path = "{id}")
  public ResponseEntity<TicketResponse> put(
      @RequestHeader("Content-Type") String contentType,
      @PathVariable UUID id,
      @Valid @RequestBody TicketRequest request) {
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

  //eliminar ticket JSON y XML
  /**
   * Accion DELETE para eliminar un ticket
   * @param id UUID
   * @return ResponseEntity
   */
  @Operation(
      summary = "eliminar ticket JSON y XML",
      description = "Accion DELETE para eliminar un ticket"
  )
  @DeleteMapping(path = "{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.ticketService.delete(id);
    return ResponseEntity.noContent().build();
  }

  //obtener precio de vuelo para ticket JSON
  /**
   * Accion GET para obtener el precio de un ticket segun
   * el precio del vuelo a tomar, respuesta en JSON
   * @param flyId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener precio de vuelo para ticket JSON",
      description = "Accion GET para obtener el precio de un ticket segun el precio del vuelo a tomar, respuesta en JSON"
  )
  @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FlyPriceResponse> getFlyPriceJson(
      @RequestParam Long flyId,
      HttpServletRequest request
  ) {
    return getFlyPrice(flyId, request.getLocale());
  }

  //obtener precio de vuelo para ticket XML
  /**
   * Accion GET para obtener el precio de un ticket segun
   * el precio del vuelo a tomar, respuesta en XML
   * @param flyId Long
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener precio de vuelo para ticket XML",
      description = "Accion GET para obtener el precio de un ticket segun el precio del vuelo a tomar, respuesta en XML"
  )
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<FlyPriceResponse> getFlyPriceXml(
      @RequestParam Long flyId,
      HttpServletRequest request
  ) {
    return getFlyPrice(flyId, request.getLocale());
  }

  //private methods
  /**
   * Metodo para traer el precio del vuelo del ticket realizado
   * @param flyId Long
   * @return ResponseEntity
   */
  private ResponseEntity<FlyPriceResponse> getFlyPrice(Long flyId, Locale customerLocale) {
    BigDecimal flyPrice = ticketService.findPrice(flyId, customerLocale);

    /* Crear una clase FlyPriceResponse para representar la respuesta en JSON y XML*/
    FlyPriceResponse response = new FlyPriceResponse(flyPrice);
    return ResponseEntity.ok(response);
  }
}
