package dev.camilo.demo.infraestructure.abstract_services;

/**
 * Interfaz generica
 * @param <RequestObj>
 * @param <ResponseObj>
 * @param <IdType>
 */
public interface CrudService<RequestObj,ResponseObj,IdType> {

  /**
   * Crear elemento
   * @param request DTO Request
   * @return DTO Response
   */
  ResponseObj create(RequestObj request);

  /**
   * Obtener elemento
   * @param id Tipo de Identificador
   * @return DTO Response
   */
  ResponseObj read(IdType id);

  /**
   * Actualizar elemento
   * @param request DTO Request
   * @param id Tipo de Identificador
   * @return DTO Response
   */
  ResponseObj update(RequestObj request, IdType id);

  /**
   * Eliminar elemento
   * @param id Tipo de Identificador
   */
  void delete(IdType id);
}
