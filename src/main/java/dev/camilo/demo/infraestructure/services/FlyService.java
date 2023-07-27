package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.api.models.responses.FlyResponse;
import dev.camilo.demo.domain.entities.Fly;
import dev.camilo.demo.domain.repositories.FlyRepository;
import dev.camilo.demo.infraestructure.abstract_services.IFlyService;
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
 * Service para FlyController
 */
@Transactional //gestion de transacciones
@Service
@Slf4j
@RequiredArgsConstructor
public class FlyService implements IFlyService {

  //repositorios inyectados
  private final FlyRepository flyRepository;

  //obtener vuelos paginados
  /**
   * Obtener vuelos con paginacion y ordenamiento
   * @param page Integer
   * @param size Integer
   * @param sortType Enum
   * @return DTO Response
   */
  @Override
  public Page<FlyResponse> readAll(Integer page, Integer size, SortType sortType) {
    PageRequest pageRequest = null;
    switch (sortType){
      case NONE -> pageRequest = PageRequest.of(page,size);
      case LOWER -> pageRequest = PageRequest
          .of(page,size, Sort.by(FIEL_BY_SORT).ascending());
      case UPPER -> pageRequest = PageRequest
          .of(page,size,Sort.by(FIEL_BY_SORT).descending());
    }
    return this.flyRepository.findAll(pageRequest).map(this::entityToResponse);
  }

  //obtener vuelos de menor precio
  /**
   * Obtener los vuelos por debajo del precio
   * @param price BigDecimal
   * @return Set
   */
  @Override
  public Set<FlyResponse> readLessPrice(BigDecimal price) {
    return this.flyRepository.selectLessPrice(price)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //obtener vuelos entre los precios
  /**
   * Obtener vuelos entre el precio min y max
   * @param min BigDecimal
   * @param max BigDecimal
   * @return Set
   */
  @Override
  public Set<FlyResponse> readBetweenPrices(BigDecimal min, BigDecimal max) {
    return this.flyRepository.selectBetweenPrice(min,max)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //obtener vuelos de origen y destino

  /**
   * Obtener los vuelos correspondientes a los origenes y destinos
   * @param origen String
   * @param destiny String
   * @return Set
   */
  @Override
  public Set<FlyResponse> readByOriginDestiny(String origen, String destiny) {
    return this.flyRepository.selectOriginDestiny(origen,destiny)
        .stream()
        .map(this::entityToResponse)
        .collect(Collectors.toSet());
  }

  //Mapeo de entity a DTO Response
  /**
   * Metodo para mapear y convertir una entidad en el DTO response
   * @param entity Fly
   * @return DTO FlyResponse
   */
  private FlyResponse entityToResponse(Fly entity){
    FlyResponse response = new FlyResponse();
    BeanUtils.copyProperties(entity,response);
    return response;
  }
}
