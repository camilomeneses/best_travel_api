package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.responses.HotelResponse;

import java.util.Set;

/**
 * Interfaz concreta
 */
public interface IHotelService extends CatalogService<HotelResponse>{

  /**
   * Metodo para traer los hoteles con puntaje mayor al rating
   * @param rating Integer
   * @return Set
   */
  Set<HotelResponse> readGraterThan(Integer rating);
}
