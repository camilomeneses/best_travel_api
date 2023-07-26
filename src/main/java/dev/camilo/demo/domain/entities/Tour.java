package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "tour")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*mapeo inverso*///check
  @OneToMany(
      mappedBy = "tour",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true
  )
  private Set<Reservation> reservations;

  /*mapeo inverso - Ticket*///check
  @OneToMany(
      mappedBy = "tour",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true
  )
  private Set<Ticket> tickets;

  /*mapeo directo customer*///check
  @ManyToOne
  @JoinColumn(name = "id_customer")
  private Customer customer;
}
