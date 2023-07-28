package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.responses.HotelResponse;
import dev.camilo.demo.domain.entities.HotelEntity;
import dev.camilo.demo.domain.repositories.HotelRepository;
import dev.camilo.demo.infraestructure.abstract_services.IHotelService;
import dev.camilo.demo.util.SortType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service para HotelController
 */
@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class HotelService implements IHotelService {

  //repositorios inyectados
  private final HotelRepository hotelRepository;

  //Obtener los hoteles paginados y ordenados

  /**
   * Obtener los hoteles paginados y ordenados, el ordenamiento
   * por default es NONE
   *
   * @param page     Integer
   * @param size     Integer
   * @param sortType Enum
   * @return Page
   */
  @Override
  public Page<HotelResponse> readAll(Integer page, Integer size, SortType sortType) {
    PageRequest pageRequest = null;
    switch (sortType) {
      case NONE -> pageRequest = PageRequest.of(page, size);
      case LOWER -> pageRequest = PageRequest
          .of(page, size, Sort.by(FIEL_BY_SORT).ascending());
      case UPPER -> pageRequest = PageRequest
          .of(page, size, Sort.by(FIEL_BY_SORT).descending());
    }
    return this.hotelRepository.findAll(pageRequest).map(this::entityToResponse);
  }

  //Obtener hoteles menores al precio

  /**
   * Obtener los hoteles menores al precio del parametro
   *
   * @param price BigDecimal
   * @return Set
   */
  @Override
  public Set<HotelResponse> readLessPrice(BigDecimal price) {
    return this.hotelRepository.findByPriceLessThan(price)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //obtener hoteles entre los precios

  /**
   * Obtener los hoteles entre los precios
   *
   * @param min BigDecimal
   * @param max BigDecimal
   * @return Set
   */
  @Override
  public Set<HotelResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
    return this.hotelRepository.findByPriceBetween(min, max)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //obtener hoteles con rating mayor a

  /**
   * Obtener todos los hoteles con rating mayor al parametro
   *
   * @param rating Integer
   * @return Set
   */
  @Override
  public Set<HotelResponse> readGraterThan(Integer rating) {
    return this.hotelRepository.findByRatingGreaterThan(rating)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //Mapeo de entity a DTO Response
  /**
   * Metodo para mapear y convertir una entidad en el DTO response
   *
   * @param entity Fly
   * @return DTO FlyResponse
   */
  private HotelResponse entityToResponse(HotelEntity entity) {
    HotelResponse response = new HotelResponse();
    BeanUtils.copyProperties(entity, response);
    return response;
  }
}
