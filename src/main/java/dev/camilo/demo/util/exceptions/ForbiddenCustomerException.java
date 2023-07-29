package dev.camilo.demo.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException{

  public ForbiddenCustomerException(){
    super("This customer is blocked");
  }
}
