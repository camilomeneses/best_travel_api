package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import dev.camilo.demo.infraestructure.abstract_services.ModifyUserService;
import dev.camilo.demo.util.enums.Documents;
import dev.camilo.demo.util.exceptions.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
/*Este servicio maneja transacciones*/
@Transactional
public class AppUserService implements ModifyUserService /*, UserDetailsService*/ {

  //repositorios inyectados
  private final AppUserRepository appUserRepository;

  /**
   * Metodo para cambiar el estado de habilitado en usuario
   *
   * @param username String
   * @return Map
   */
  @Override
  public Map<String, Boolean> enabled(String username) {
    /*traer el usuario*/
    var user = this.appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
    /*habilitar / desabilitar*/
    user.setEnabled(!user.isEnabled());
    /*guardar*/
    var userSaved = this.appUserRepository.save(user);
    /*responder con SingletonMap*/
    return Collections.singletonMap(userSaved.getUsername(), userSaved.isEnabled());
  }

  /**
   * Metodo para agregar un rol a usuario
   *
   * @param username String
   * @param role     String
   * @return Map
   */
  @Override
  public Map<String, List<String>> addRole(String username, String role) {
    /*traer usuario*/
    var user = this.appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
    /*asignar role nuevo*/
    user.getRole().getGrantedAuthorities().add(role);
    /*guardar usuario con authoriries*/
    var userSaved = this.appUserRepository.save(user);
    /*devolver list de granted_authorities*/
    var authorities = userSaved.getRole().getGrantedAuthorities()
        .stream().toList();
    /*responder con Map*/
    log.info("User {} add Role {}", userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities().toString());
    return Collections.singletonMap(userSaved.getUsername(), authorities);
  }

  @Override
  public Map<String, List<String>> removeRole(String username, String role) {
    /*traer usuario*/
    var user = this.appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
    /*quitar role*/
    user.getRole().getGrantedAuthorities().remove(role);
    /*guardar usuario con authoriries*/
    var userSaved = this.appUserRepository.save(user);
    /*devolver list de granted_authorities*/
    var authorities = userSaved.getRole().getGrantedAuthorities()
        .stream().toList();
    /*responder con Map*/
    log.info("User {} remove Role {}", userSaved.getUsername(),userSaved.getRole().getGrantedAuthorities().toString());
    return Collections.singletonMap(userSaved.getUsername(), authorities);
  }

  @Transactional(readOnly = true)
  private void loadUserByUsername(String username){
    /*traer usuario*/
    var user = this.appUserRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
  }
}
