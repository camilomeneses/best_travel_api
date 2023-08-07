package dev.camilo.demo.infraestructure.abstract_services;

import java.util.List;
import java.util.Map;

/**
 * Interfaz para Modificacion de permisos en Usuario
 */
public interface ModifyUserService {
  /**
   * habilitar usuario
   * @param username String
   * @return Map
   */
  Map<String,Boolean> enabled(String username);

  /**
   * agregar role a usuario
   * @param username String
   * @param role String
   * @return Map
   */
  Map<String, List<String>> addRole(String username, String role);

  /**
   * remover role a usuario
   * @param username String
   * @param role String
   * @return Map
   */
  Map<String, List<String>> removeRole(String username, String role);
}
