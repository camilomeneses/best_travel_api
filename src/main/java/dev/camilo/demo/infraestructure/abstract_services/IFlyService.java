package dev.camilo.demo.infraestructure.abstract_services;

import dev.camilo.demo.api.models.responses.FlyResponse;

import java.util.Set;

/**
 * Interfaz concreta
 */
public interface IFlyService extends CatalogService<FlyResponse> {

  /**
   * obtener vuelo segun origen y destino
   * @param origen String
   * @param destiny String
   * @return Set
   */
  Set<FlyResponse> readByOriginDestiny(String origen, String destiny);
}
