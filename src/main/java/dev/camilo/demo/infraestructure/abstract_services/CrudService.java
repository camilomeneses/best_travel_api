package dev.camilo.demo.infraestructure.abstract_services;

/*Interfaz Generica*/
public interface CrudService<RequestObj,ResponseObj,IdType> {
  ResponseObj create(RequestObj request);
  ResponseObj read(IdType id);
  ResponseObj update(RequestObj request, IdType id);
  void delete(IdType id);
}
