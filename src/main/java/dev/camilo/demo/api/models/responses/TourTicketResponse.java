package dev.camilo.demo.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TourTicketResponse implements Serializable {
  /**
   * id de ticket
   */
  private UUID ticketId;
}
