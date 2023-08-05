package dev.camilo.demo.domain.entities.jpa;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * Entidad Customer
 *
 *  <ul>
 *    <li><b>dni</b> String</li>
 *    <li><b>fullName</b> String</li>
 *    <li><b>creditCard</b> String</li>
 *    <li><b>phoneNumber</b> String</li>
 *    <li><b>totalFlights</b> Integer</li>
 *    <li><b>totalLodgings</b> Integer</li>
 *    <li><b>totalTours</b> Integer</li>
 *    <li><b>tour</b> Set de TourEntity <small>(relacion inversa)</small></li>
 *    <li><b>reservations</b> Set de ReservationEntity <small>(relacion inversa)</small></li>
 *    <li><b>tickets</b> Set de TicketEntity <small>(relacion inversa)</small></li>
 *  </ul>
 */
@Entity(name = "customer")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class CustomerEntity {

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
  /*FetchType.LAZY arregla problema de Delete*/
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.LAZY
  )
  private Set<TourEntity> tour;

  /*mapeo inverso customer_id - Reservation*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.LAZY
  )
  private Set<ReservationEntity> reservations;

  /*mapeo inverso customer_id - Ticket*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "customer",
      fetch = FetchType.LAZY
  )
  private Set<TicketEntity> tickets;
}
