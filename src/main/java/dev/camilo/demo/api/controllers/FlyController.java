package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.infraestructure.abstract_services.IFlyService;
import dev.camilo.demo.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * Controller REST para respuestas JSON y XML para fly
 */
@RestController
@RequestMapping(path = "fly")
@RequiredArgsConstructor
public class FlyController {

  //services inyectados
  private final IFlyService flyService;

  //Obtener todos los vuelos paginados y ordenados JSON
  /**
   * Obtener los vuelos paginados y ordenados
   * @param page Integer
   * @param size Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<FlyResponse>> getAllJson(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
      ){
    return getPageResponse(page, size, sortType);
  }

  //Obtener todos los vuelos paginados y ordenados XML
  /**
   * Obtener los vuelos paginados y ordenados
   * @param page Integer
   * @param size Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Page<FlyResponse>> getAllXml(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
  ){
    return getPageResponse(page, size, sortType);
  }

  //private methods
  /**
   * Metodo para obtener los vuelos paginados y ordenados,
   * si no viene ordenamiento se le asigna el por defecto NONE
   * @param page Integer
   * @param size Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  private ResponseEntity<Page<FlyResponse>> getPageResponse(Integer page, Integer size, SortType sortType) {
    /*validar si viene ordenamiento*/
    if(Objects.isNull(sortType)){
      sortType = SortType.NONE;
    }
    var response = flyService.readAll(page, size, sortType);
    return response.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(response);
  }

}
