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
