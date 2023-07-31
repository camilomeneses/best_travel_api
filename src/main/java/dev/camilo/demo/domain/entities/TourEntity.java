package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Entidad Customer
 *
 *  <ul>
 *    <li><b>id</b> Long</li>
 *    <li><b>reservations</b> Set de ReservationEntity <small>(relacion inversa)</small></li>
 *    <li><b>tickets</b> Set de TicketEntity <small>(relacion inversa)</small></li>
 *    <li><b>customer</b> Set de Customer <small>(relacion inversa)</small></li>
 *  </ul>
 */
@Entity(name = "tour")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class TourEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*mapeo inverso*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      mappedBy = "tour",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  private Set<ReservationEntity> reservations;

  /*mapeo inverso - Ticket*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      mappedBy = "tour",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true
  )
  private Set<TicketEntity> tickets;

  /*mapeo directo customer*///check
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_customer")
  private CustomerEntity customer;

  /**
   * actualizar tickets y reservaciones antes de persistir o remover
   */
  @PrePersist
  @PreRemove
  public void updateFK() {
    this.tickets.forEach(ticket -> ticket.setTour(this));
    this.reservations.forEach(reservation -> reservation.setTour(this));
  }

  //tratamiento de tickets

  /**
   * Agregar ticket a el HashSet de tickets, se hace relacion inversa
   * para agregar el id_tour a cada ticket correspondiente (llave foranea)
   *
   * @param ticket TickerEntity
   */
  public void addTicket(TicketEntity ticket) {
    if (Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
    this.tickets.add(ticket);
    this.tickets.forEach(t -> t.setTour(this));
  }

  /**
   * eliminar ticket por id
   *
   * @param id UUID
   */
  public void removeTicket(UUID id) {
    this.tickets.forEach(ticket -> {
      if (ticket.getId().equals(id)) {
        ticket.setTour(null);
      }
    });
  }

  //tratamiento de reservations

  /**
   * Agregar reservation a el HashSet de reservations, se hace relacion inversa
   * para agregar el id_tour a cada reservation correspondiente (llave foranea)
   *
   * @param reservation ReservationEntity
   */
  public void addReservation(ReservationEntity reservation) {
    if (Objects.isNull(this.reservations)) this.reservations = new HashSet<>();
    this.reservations.add(reservation);
    this.reservations.forEach(r -> r.setTour(this));
  }

  public void removeReservation(UUID id) {
    this.reservations.forEach((reservation -> {
      if(reservation.getId().equals(id)) {
        reservation.setTour(null);
      }
    }));
  }
}
