package dev.camilo.demo.infraestructure.services;

import dev.camilo.demo.domain.entities.documents.AppUserDocument;
import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import dev.camilo.demo.infraestructure.abstract_services.ModifyUserService;
import dev.camilo.demo.util.annotations.BlackListCheck;
import dev.camilo.demo.util.enums.Documents;
import dev.camilo.demo.util.exceptions.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
/*Este servicio maneja transacciones*/
@Transactional
public class AppUserService implements ModifyUserService , UserDetailsService {

  //repositorios inyectados
  private final AppUserRepository appUserRepository;

  /**
   * Metodo para cambiar el estado de habilitado en usuario
   *
   * @param username String
   * @return Map
   */
  @Override
  @BlackListCheck
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
  @BlackListCheck
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

  /**
   * Metodo para remover un rol a un usuario
   * @param username String
   * @param role String
   * @return Map
   */
  @Override
  @BlackListCheck
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

  /**
   * Metodo para sustituir el metodo loadUserByUsername del UserDetailsService
   * donde traemos la informacion del usuario desde mongoDB
   * @param username String
   * @return UserDetails
   */
  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) {
    var user = this.appUserRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
    return mapUserToUserDetails(user);
  }

  /**
   * Metodo que devuelve la informacion referente a un usuario de tipo UserDetails,
   * con nombre, password, habilitado, no expira cuenta, credenciales, cuenta no bloqueda
   * y su set de authorities
   * @param user AppUserDocument
   * @return UserDetails
   */
  private static UserDetails mapUserToUserDetails(AppUserDocument user) {
    Set<GrantedAuthority> authorities = user.getRole()
        .getGrantedAuthorities()
        .stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());
    return new User(
        user.getUsername(),
        user.getPassword(),
        user.isEnabled(),
        true,
        true,
        true,
        authorities
    );
  }
}
