package dev.camilo.demo.api.models.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO Request para TourController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "TourRequest")
public class TourRequest implements Serializable {
  /**
   * id de customer
   */
  @Size(min = 10,max = 20, message = "The size have to a length between 18 and 20 characters")
  @NotBlank(message = "Id customer is mandatory")
  public String customerId;
  /**
   * vuelos a vincular a tour del customer
   */
  @Size(min=1, message = "Min flight tour per tour")
  private Set<TourFlyRequest> flights;
  /**
   * hoteles a vincular a tour de customer
   */
  @Size(min=1, message = "Min hotel tour per tour")
  private Set<TourHotelRequest> hotels;
}
