package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity(name = "customer")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Customer {

  @Id
  private String dni;
  @Column(length = 50)
  private String fullName;
  @Column(length = 20)
  private String creditCard;
  @Column(length = 12)
  private String phoneNumber;
  private Integer totalFlights;
  private Integer totalLodgings;
  private Integer totalTours;

  /*mapeo inverso customer_id - Tour*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.EAGER
  )
  private Set<Tour> tours;

  /*mapeo inverso customer_id - Reservation*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.EAGER
  )
  private Set<Reservation> reservations;

  /*mapeo inverso customer_id - Ticket*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.EAGER
  )
  private Set<Ticket> tickets;
}
