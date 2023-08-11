package dev.camilo.demo.util.classes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationEvents {

  @EventListener
  public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
    Authentication authentication = event.getAuthentication();
    log.info("Inicio de session EXITOSO para el usuario: {}", authentication.getName());
  }

  @EventListener
  public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
    Authentication authentication = event.getAuthentication();

    String username = (String) authentication.getPrincipal();
    log.warn("Inicio de session FALLIDO para el usuario: {}", username);
  }
}
