package dev.camilo.demo.api.controllers.error_handler;

import dev.camilo.demo.api.models.responses.error.BaseErrorResponse;
import dev.camilo.demo.api.models.responses.error.ErrorResponse;
import dev.camilo.demo.util.exceptions.ForbiddenCustomerException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 */
@RestControllerAdvice
@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class ForbiddenCustomerHandler {

  @ExceptionHandler(ForbiddenCustomerException.class)
  public ResponseEntity<BaseErrorResponse> handleIdNotFound(
      ForbiddenCustomerException exception,
      HttpServletRequest request
  ) {
    String contentType = request.getContentType();
    String path = request.getRequestURI();
    /*verificar si el path contiene /xml/*/
    boolean isXmlRequest = path.contains("/xml/");

    ErrorResponse errorResponse = ErrorResponse.builder()
        .message(exception.getMessage())
        .status(HttpStatus.FORBIDDEN.name())
        .errorCode(HttpStatus.FORBIDDEN.value())
        .build();

    if (isXmlRequest || MediaType.APPLICATION_XML_VALUE.equals(contentType)) {
      /*Si es una solicitud XML, responder con formato XML*/
      return ResponseEntity.badRequest()
          .contentType(MediaType.APPLICATION_XML)
          .body(errorResponse);
    } else {
      /*Si no es una solicitud XML, responder con formato JSON (por defecto)*/
      return ResponseEntity
          .badRequest()
          .contentType(MediaType.APPLICATION_JSON)
          .body(errorResponse);
    }
  }
}
