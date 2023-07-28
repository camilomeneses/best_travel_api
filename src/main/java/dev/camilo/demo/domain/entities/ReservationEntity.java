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
  private TourEntity tourEntity;

  /*mapeo directo hotel*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private HotelEntity hotelEntity;

  /*mapeo directo customer*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private CustomerEntity customerEntity;
}
