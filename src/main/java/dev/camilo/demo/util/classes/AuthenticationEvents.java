package dev.camilo.demo.util.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Clase que nos permite configurar un EventListener para cuando se logea el usuario
 * y su logeo es exitoso o ha fallado
 */
@Slf4j
@Component
public class AuthenticationEvents {

  /**
   * Metodo para escuchar el logeo cuando es exitoso
   * @param event AuthenticationSuccessEvent
   */
  @EventListener
  public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
    Authentication authentication = event.getAuthentication();
    log.info("Inicio de session EXITOSO para el usuario: {}", authentication.getName());
  }

  /**
   * Metodo para escuchar el logeo cuando es fallido
   * @param event AbstractAuthenticationFailureEvent
   */
  @EventListener
  public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
    Authentication authentication = event.getAuthentication();

    String username = (String) authentication.getPrincipal();
    log.warn("Inicio de session FALLIDO para el usuario: {}", username);
  }
}
