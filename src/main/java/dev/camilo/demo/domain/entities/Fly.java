package dev.camilo.demo.domain.entities;

import dev.camilo.demo.util.Aeroline;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Aeroline aeroLine;

  /*un vuelo puede estar en varios tickets*/

  /*mapeo inverso fly_id - Ticket (check)*/
  @OneToMany(
      mappedBy = "fly",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true
  )
  private Set<Ticket> tickets;
}
