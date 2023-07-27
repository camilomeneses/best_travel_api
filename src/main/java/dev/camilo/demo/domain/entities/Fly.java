package dev.camilo.demo.domain.entities;

import dev.camilo.demo.util.AeroLine;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "fly")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class Fly {

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
  private Set<Ticket> tickets;
}
