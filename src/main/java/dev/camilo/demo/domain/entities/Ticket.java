package dev.camilo.demo.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Ticket {

  @Id
  private UUID id;
  private LocalDateTime departureDate;
  private LocalDateTime arrivalDate;
  private LocalDate purchaseDate;
  private BigDecimal price;
  /*mapeo directo tour*///check
  @ManyToOne
  @JoinColumn(name = "tour_id", nullable = true)
  private Tour tour;

  /*mapeo directo fly*///check
  @ManyToOne
  @JoinColumn(name = "fly_id")
  private Fly fly;

  /*mapeo directo customer*///check
  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
