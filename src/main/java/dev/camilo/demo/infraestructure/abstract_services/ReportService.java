package dev.camilo.demo.infraestructure.abstract_services;

/**
 * Interfaz para establecer el metodo para leer archivos
 */
public interface ReportService {

  /**
   * Metodo para leer el archivo
   * @return byte[]
   */
  byte[] readFile();
}
