package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad Customer
 *
 *  <ul>
 *    <li><b>id</b> UUID</li>
 *    <li><b>departureDate</b> LocalDateTime</li>
 *    <li><b>arrivalDate</b> LocalDateTime</li>
 *    <li><b>purchaseDate</b> LocalDate</li>
 *    <li><b>price</b> BigDecimal</li>
 *    <li><b>tour</b> Set de TourEntity <small>(llave foranea)</small></li>
 *    <li><b>fly</b> Set de FlyEntity <small>(llave foranea)</small></li>
 *    <li><b>customer</b> Set de CustomerEntity <small>(llave foranea)</small></li>
 *  </ul>
 */
@Entity(name = "ticket")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class TicketEntity {

  @Id
  private UUID id;
  private LocalDateTime departureDate;
  private LocalDateTime arrivalDate;
  private LocalDate purchaseDate;
  private BigDecimal price;
  /*mapeo directo tour*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id", nullable = true)
  private TourEntity tour;

  /*mapeo directo fly*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "fly_id")
  private FlyEntity fly;

  /*mapeo directo customer*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;
}
