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
 *    <li><b>dateTimeReservation</b> LocalDateTime</li>
 *    <li><b>dateStart</b> LocalDate</li>
 *    <li><b>dateEnd</b> LocalDate</li>
 *    <li><b>totalDays</b> Integer</li>
 *    <li><b>price</b> BigDecimal</li>
 *    <li><b>tour</b> Set de TourEntity <small>(llave foranea)</small></li>
 *    <li><b>hotel</b> Set de HotelEntity <small>(llave foranea)</small></li>
 *    <li><b>customer</b> Set de CustomerEntity <small>(llave foranea)</small></li>
 *  </ul>
 */
@Entity(name = "reservation")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ReservationEntity {

  @Id
  private UUID id;

  /*timestamp*/
  @Column(name = "date_reservation")
  private LocalDateTime dateTimeReservation;
  private LocalDate dateStart;

  @Column(nullable = true)
  private LocalDate dateEnd;
  private Integer totalDays;
  private BigDecimal price;

  /*mapeo directo tour*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id", nullable = true)
  private TourEntity tour;

  /*mapeo directo hotel*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private HotelEntity hotel;

  /*mapeo directo customer*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private CustomerEntity customer;
}
