package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

  public void isInBlackListCustomer(String customerId){
    if(customerId.equals("VIKI771012HMCRG093")){
      throw new ForbiddenCustomerException();
    }
  }
}
