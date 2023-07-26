package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

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
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      mappedBy = "tour",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true
  )
  private Set<Reservation> reservations;

  /*mapeo inverso - Ticket*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
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

  /*relacion inversa de los tickets*/
  //add, update and remove tickets
  public void addTicket(Ticket ticket){
    this.tickets.add(ticket);
  }

  /*seteo del tour actual a los ticket*/
  public void updateTickets(){
    this.tickets.forEach(ticket -> ticket.setTour(this));
  }

  public void removeTicket(UUID id){
    this.tickets.removeIf(ticket -> ticket.getId().equals(id));
  }

    /*relacion inversa de las reservaciones*/
    //add, update and remove reservations
    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }

    public void updateReservation(){
      this.reservations.forEach(reservation -> reservation.setTour(this));
    }

    public  void removeReservation(UUID id){
      this.reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
