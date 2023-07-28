package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
  public void updateFK(){
    this.tickets.forEach(ticket -> ticket.setTour(this));
    this.reservations.forEach(reservation -> reservation.setTour(this));
  }

  /**
   * eliminar ticket por id
   * @param id UUID
   */
  public void removeTicket(UUID id){
    this.tickets.forEach(ticket -> {
      if(ticket.getId().equals(id)){
        ticket.setTour(null);
      }
    });
  }

  /**
   * Agrear ticket a el HashSet de tickets, se hace relacion inversa
   * para agregar en las reservaciones este tour
   * @param ticket
   */
  public void addTicket(TicketEntity ticket){
    if(Objects.isNull(this.tickets)) this.tickets = new HashSet<>();
    this.tickets.add(ticket);
    this.reservations.forEach(reservation -> reservation.setTour(this));
  }
}
