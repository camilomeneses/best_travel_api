package dev.camilo.demo.api.models.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO Request para TourController
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JacksonXmlRootElement(localName = "flights")
public class TourFlyRequest implements Serializable {
  /**
   * id de fly
   */
  @Positive(message = "Must be greater than 0")
  @NotNull(message = "Id is mandatory")
  public Long id;
}
