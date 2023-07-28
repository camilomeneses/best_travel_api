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

  /*relacion inversa de los tickets*/
  //add, update and remove tickets
  public void addTicket(TicketEntity ticketEntity){
    if(Objects.isNull(this.tickets)){
      this.tickets = new HashSet<>();
    }
    this.tickets.add(ticketEntity);
  }

  /*seteo del tour actual a los ticket*/
  public void updateTickets(){
    this.tickets.forEach(ticket -> ticket.setTour(this));
  }

  public void removeTicket(UUID id){
    if(Objects.isNull(this.tickets)){
      this.tickets = new HashSet<>();
    }
    this.tickets.removeIf(ticket -> ticket.getId().equals(id));
  }

    /*relacion inversa de las reservaciones*/
    //add, update and remove reservations
    public void addReservation(ReservationEntity reservationEntity){
      if(Objects.isNull(this.reservations)){
        this.reservations = new HashSet<>();
      }
      this.reservations.add(reservationEntity);
    }

    public void updateReservations(){
      this.reservations.forEach(reservation -> reservation.setTourEntity(this));
    }

    public  void removeReservation(UUID id){
      if(Objects.isNull(this.reservations)){
        this.reservations = new HashSet<>();
      }
      this.reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
