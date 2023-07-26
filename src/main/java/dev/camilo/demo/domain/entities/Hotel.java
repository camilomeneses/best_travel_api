package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "hotel")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Hotel {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50)
  private String name;
  @Column(length = 50)
  private String address;
  private Integer rating;
  private BigDecimal price;

  /*mapeo inverso hotel_id - Reservation*///check
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "hotel",
      fetch = FetchType.EAGER
  )
  private Set<Reservation> reservations;
}
