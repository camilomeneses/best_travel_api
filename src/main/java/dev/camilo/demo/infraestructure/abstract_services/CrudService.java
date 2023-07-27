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
   * @param request
   * @return
   */
  ResponseObj create(RequestObj request);

  /**
   * Obtener elemento
   * @param id
   * @return
   */
  ResponseObj read(IdType id);

  /**
   * Actualizar elemento
   * @param request
   * @param id
   * @return
   */
  ResponseObj update(RequestObj request, IdType id);

  /**
   * Eliminar elemento
   * @param id
   */
  void delete(IdType id);
}
