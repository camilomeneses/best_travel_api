package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

/**
 * Metodo para Agregar un usuario a la lista de bloqueados en el sistema
 */
@Component
public class BlackListHelper {

  /**
   * Metodo de validacion para un usuario bloqueado
   * @param customerId String
   */
  public void isInBlackListCustomer(String customerId){
    if(customerId.equals("VIKI771012HMCRG093")){
      throw new ForbiddenCustomerException();
    }
  }
}
