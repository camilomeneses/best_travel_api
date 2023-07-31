package dev.camilo.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Entidad Hotel
 *
 *  <ul>
 *    <li><b>id</b> Long</li>
 *    <li><b>name</b> String</li>
 *    <li><b>address</b> String</li>
 *    <li><b>rating</b> Integer</li>
 *    <li><b>price</b> BigDecimal</li>
 *    <li><b>reservations</b> Set de ReservationEntity <small>(relacion inversa)</small></li>
 *  </ul>
 */
@Entity(name = "hotel")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class HotelEntity {
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
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      mappedBy = "hotel",
      fetch = FetchType.LAZY
  )
  private Set<ReservationEntity> reservations;
}
