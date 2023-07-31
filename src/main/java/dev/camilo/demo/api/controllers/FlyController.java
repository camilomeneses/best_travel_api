package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.infraestructure.abstract_services.IFlyService;
import dev.camilo.demo.util.enums.SortType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Controller REST para respuestas JSON y XML para fly
 */
@Tag(name = "Fly")
@RestController
@RequestMapping(path = "fly")
@RequiredArgsConstructor
public class FlyController {

  //services inyectados
  private final IFlyService flyService;

  //Obtener todos los vuelos paginados y ordenados JSON

  /**
   * Obtener los vuelos paginados y ordenados
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener todos los vuelos paginados y ordenados JSON",
      description = "Obtener los vuelos paginados y ordenados"
  )
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<FlyResponse>> getAllJson(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
  ) {
    return getPageResponse(page, size, sortType);
  }

  //Obtener todos los vuelos paginados y ordenados XML

  /**
   * Obtener los vuelos paginados y ordenados
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener todos los vuelos paginados y ordenados XML",
      description = "Obtener los vuelos paginados y ordenados"
  )
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Page<FlyResponse>> getAllXml(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
  ) {
    return getPageResponse(page, size, sortType);
  }

  //Obtener vuelos menores al precio JSON

  /**
   * Obtener los vuelos menores al precio del parametro,
   * response en formato JSON
   *
   * @param price BigDecimal
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener vuelos menores al precio JSON",
      description = "Obtener los vuelos menores al precio del parametro,\n" +
          "   response en formato JSON"
  )
  @GetMapping(path = "/less_price", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<FlyResponse>> getLessPriceJson(
      @RequestParam BigDecimal price
  ) {
    return getSetResponse(flyService.readLessPrice(price));
  }

  //Obtener vuelos menores al precio XML

  /**
   * Obtener los vuelos menores al precio del parametro,
   * response en formato XML
   *
   * @param price BigDecimal
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener vuelos menores al precio XML",
      description = "Obtener los vuelos menores al precio del parametro,\n" +
          "   response en formato XML"
  )
  @GetMapping(path = "/xml/less_price", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<FlyResponse>> getLessPriceXml(
      @RequestParam BigDecimal price
  ) {
    return getSetResponse(flyService.readLessPrice(price));
  }

  //Obtener vuelos entre los precios JSON

  /**
   * Obtener los vuelos con precios entre el min y el max precio,
   * response formato JSON
   *
   * @param min BigDecimal
   * @param max BigDecimal
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener vuelos entre los precios JSON",
      description = "Obtener los vuelos con precios entre el min y el max precio,\n" +
          "   response formato JSON"
  )
  @GetMapping(path = "/between_price", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<FlyResponse>> getBetweenPriceJson(
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max
  ) {
    return getSetResponse(flyService.readBetweenPrices(min, max));
  }

  //Obtener vuelos entre los precios XML

  /**
   * Obtener los vuelos con precios entre el min y el max precio,
   * response formato XML
   *
   * @param min BigDecimal
   * @param max BigDecimal
   * @return ResponseEntity
   */
  @Operation(
      summary = "Obtener vuelos entre los precios XML",
      description = "Obtener los vuelos con precios entre el min y el max precio,\n" +
          "   response formato XML"
  )
  @GetMapping(path = "/xml/between_price", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<FlyResponse>> getBetweenPriceXml(
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max
  ) {
    return getSetResponse(flyService.readBetweenPrices(min, max));
  }

  //obtener vuelos segun origen y destino JSON

  /**
   * Obtener los vuelos segun los parametros de entrada de origen y destino,
   * response en formato JSON
   *
   * @param origin  String
   * @param destiny String
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener vuelos segun origen y destino JSON",
      description = "Obtener los vuelos segun los parametros de entrada de origen y destino,\n" +
          "   response en formato JSON"
  )
  @GetMapping(path = "origin_destiny", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<FlyResponse>> getByOriginDestinyJson(
      @RequestParam String origin,
      @RequestParam String destiny
  ) {
    return getSetResponse(flyService.readByOriginDestiny(origin, destiny));
  }

  //obtener vuelos segun origen y destino XML

  /**
   * Obtener los vuelos segun los parametros de entrada de origen y destino,
   * response en formato XML
   *
   * @param origin  String
   * @param destiny String
   * @return ResponseEntity
   */
  @Operation(
      summary = "obtener vuelos segun origen y destino XML",
      description = "Obtener los vuelos segun los parametros de entrada de origen y destino,\n" +
          "   response en formato XML"
  )
  @GetMapping(path = "/xml/origin_destiny", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<FlyResponse>> getByOriginDestinyXml(
      @RequestParam String origin,
      @RequestParam String destiny
  ) {
    return getSetResponse(flyService.readByOriginDestiny(origin, destiny));
  }


  //private methods

  /**
   * Metodo para obtener los vuelos paginados y ordenados,
   * si no viene ordenamiento se le asigna el por defecto NONE
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  private ResponseEntity<Page<FlyResponse>> getPageResponse(Integer page, Integer size, SortType sortType) {
    /*validar si viene ordenamiento*/
    if (Objects.isNull(sortType)) {
      sortType = SortType.NONE;
    }
    var response = flyService.readAll(page, size, sortType);
    return response.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(response);
  }

  /**
   * Metodo para obtener los vuelos segun los filtros correspondientes,
   * - readLessPrice
   * - readBetweenPrices
   * - readByOriginDestiny
   *
   * @return ResponseEntity
   */
  private ResponseEntity<Set<FlyResponse>> getSetResponse(Set<FlyResponse> flyService) {
    var response = flyService;
    return response.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(response);
  }


}
