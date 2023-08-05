package dev.camilo.demo.domain.entities.jpa;

import dev.camilo.demo.util.enums.AeroLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Entidad Fly
 *
 *  <ul>
 *    <li><b>id</b> Double</li>
 *    <li><b>originLat</b> Double</li>
 *    <li><b>originLng</b> Double</li>
 *    <li><b>destinyLat</b> Double</li>
 *    <li><b>destinyLng</b> Double</li>
 *    <li><b>originName</b> String</li>
 *    <li><b>destinyName</b> String</li>
 *    <li><b>aeroLine</b> Enum</li>
 *    <li><b>tickets</b> Set de TicketEntity <small>(relacion inversa)</small></li>
 *  </ul>
 */
@Entity(name = "fly")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class FlyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Double originLat;
  private Double originLng;
  private Double destinyLat;
  private Double destinyLng;
  @Column(length = 20)
  private String originName;
  @Column(length = 20)
  private String destinyName;
  private BigDecimal price;
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private AeroLine aeroLine;

  /*un vuelo puede estar en varios tickets*/

  /*mapeo inverso fly_id - Ticket*///check
  //Excluir ToString y Equals infinito
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "fly",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private Set<TicketEntity> tickets;
}
