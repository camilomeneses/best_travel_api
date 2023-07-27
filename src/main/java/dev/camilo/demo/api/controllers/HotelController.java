package dev.camilo.demo.api.controllers;

import dev.camilo.demo.api.models.responses.HotelResponse;
import dev.camilo.demo.infraestructure.abstract_services.IHotelService;
import dev.camilo.demo.util.SortType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(path = "hotel")
@RequiredArgsConstructor
public class HotelController {

  //services inyectados
  private final IHotelService hotelService;

  //Obtener todos los hoteles paginados y ordenados JSON

  /**
   * Obtener los hoteles paginados y ordenados
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Page<HotelResponse>> getAllJson(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
  ) {
    return getPageResponse(page, size, sortType);
  }

  //Obtener todos los hoteles paginados y ordenados XML

  /**
   * Obtener los hoteles paginados y ordenados
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  @GetMapping(path = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Page<HotelResponse>> getAllXml(
      @RequestParam Integer page,
      @RequestParam Integer size,
      @RequestHeader(required = false) SortType sortType
  ) {
    return getPageResponse(page, size, sortType);
  }

  //Obtener hoteles menores al precio JSON

  /**
   * Obtener los hoteles menores al precio del parametro,
   * response en formato JSON
   *
   * @param price BigDecimal
   * @return ResponseEntity
   */
  @GetMapping(path = "/less_price", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<HotelResponse>> getLessPriceJson(
      @RequestParam BigDecimal price
  ) {
    return getSetResponse(hotelService.readLessPrice(price));
  }

  //Obtener hoteles menores al precio XML

  /**
   * Obtener los hoteles menores al precio del parametro,
   * response en formato XML
   *
   * @param price BigDecimal
   * @return ResponseEntity
   */
  @GetMapping(path = "/xml/less_price", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<HotelResponse>> getLessPriceXml(
      @RequestParam BigDecimal price
  ) {
    return getSetResponse(hotelService.readLessPrice(price));
  }

  //Obtener hoteles entre los precios JSON

  /**
   * Obtener los hoteles con precios entre el min y el max precio,
   * response formato JSON
   *
   * @param min BigDecimal
   * @param max BigDecimal
   * @return ResponseEntity
   */
  @GetMapping(path = "/between_price", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<HotelResponse>> getBetweenPriceJson(
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max
  ) {
    return getSetResponse(hotelService.readBetweenPrices(min, max));
  }

  //Obtener hoteles entre los precios XML

  /**
   * Obtener los hoteles con precios entre el min y el max precio,
   * response formato XML
   *
   * @param min BigDecimal
   * @param max BigDecimal
   * @return ResponseEntity
   */
  @GetMapping(path = "/xml/between_price", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<HotelResponse>> getBetweenPriceXml(
      @RequestParam BigDecimal min,
      @RequestParam BigDecimal max
  ) {
    return getSetResponse(hotelService.readBetweenPrices(min, max));
  }

  //Obtener hoteles menores al precio JSON

  /**
   * Obtener los hoteles con rating mayor al parametro,
   * response en formato JSON
   *
   * @param rating Integer
   * @return ResponseEntity
   */
  @GetMapping(path = "/greater_rating", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Set<HotelResponse>> getGreaterThanRatingJson(
      @RequestParam Integer rating
  ) {
    /*validando rating entre 1 y 5*/
    if(rating > 4) rating = 4;
    if(rating < 1) rating = 1;
    return getSetResponse(hotelService.readGraterThan(rating));
  }

  //Obtener hoteles menores al precio XML

  /**
   * Obtener los hoteles con rating mayor al parametro,
   * response en formato XML
   *
   * @param rating Integer
   * @return ResponseEntity
   */
  @GetMapping(path = "/xml/greater_rating", produces = MediaType.APPLICATION_XML_VALUE)
  public ResponseEntity<Set<HotelResponse>> getGreaterThanRatingXml(
      @RequestParam Integer rating
  ) {
    /*validando rating entre 1 y 5*/
    if(rating > 4) rating = 4;
    if(rating < 1) rating = 1;
    return getSetResponse(hotelService.readGraterThan(rating));
  }


  //private methods

  /**
   * Metodo para obtener los hoteles paginados y ordenados,
   * si no viene ordenamiento se le asigna el por defecto NONE
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return ResponseEntity
   */
  private ResponseEntity<Page<HotelResponse>> getPageResponse(Integer page, Integer size, SortType sortType) {
    /*validar si viene ordenamiento*/
    if (Objects.isNull(sortType)) {
      sortType = SortType.NONE;
    }
    var response = hotelService.readAll(page, size, sortType);
    return response.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(response);
  }

  /**
   * Metodo para obtener los hoteles segun los filtros correspondientes,
   * - readLessPrice
   * - readBetweenPrices
   * - readGraterThan
   *
   * @return ResponseEntity
   */
  private ResponseEntity<Set<HotelResponse>> getSetResponse(Set<HotelResponse> hotelService) {
    var response = hotelService;
    return response.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(response);
  }
}
