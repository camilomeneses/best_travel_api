package dev.camilo.demo.api.models.request;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
@JacksonXmlRootElement(localName = "hotels")
public class TourHotelRequest implements Serializable {
  /**
   * id de hotel
   */
  @Positive(message = "Must be greater than 0")
  @NotNull(message = "Id is mandatory")
  public Long id;
  /**
   * numero de dias a revervar hotel
   */
  @Min(value = 1, message = "Min one day to make reservation")
  @Max(value = 30, message = "Max 30 days to make reservation")
  @NotNull(message = "Total days is mandatory")
  private Integer totalDays;
}

