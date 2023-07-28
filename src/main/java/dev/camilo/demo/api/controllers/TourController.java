package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.request.TourRequest;
import dev.camilo.demo.api.models.responses.TourResponse;
import dev.camilo.demo.infraestructure.abstract_services.ITourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para respuestas JSON y XML para tour
 */
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
   * @param contentType
   * @param request
   * @return
   */
  @PostMapping
  public ResponseEntity<TourResponse> post(
      @RequestHeader("Content-Type") String contentType,
      @RequestBody TourRequest request
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
  @DeleteMapping(path = "{id}")
  public ResponseEntity<TourResponse> delete(@PathVariable Long id){
    this.tourService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
