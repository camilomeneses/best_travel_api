package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import dev.camilo.demo.util.enums.Documents;
import dev.camilo.demo.util.exceptions.ForbiddenCustomerException;
import dev.camilo.demo.util.exceptions.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Metodo para Agregar un usuario a la lista de bloqueados en el sistema
 */
@Component
@RequiredArgsConstructor
public class BlackListHelper {

  //repositorio inyectado
  private final AppUserRepository appUserRepository;

  /**
   * Metodo de validacion para un usuario bloqueado
   */
  public void isInBlackListCustomer() {
    /*nombre de usuario logeado*/
    String userName = SecurityContextHolder.getContext().getAuthentication().getName();
    /*traer user de la db por name*/
    var user = this.appUserRepository.findByUsername(userName)
        .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
    /*si no esta enabled se dispara CusomerException*/
    if (!user.isEnabled()) {
      throw new ForbiddenCustomerException();
    }
  }
}
