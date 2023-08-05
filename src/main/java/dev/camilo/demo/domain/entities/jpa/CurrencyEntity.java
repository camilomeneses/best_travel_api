package dev.camilo.demo.domain.entities.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * entidad currency de la aplicacion
 */
@Entity
@Table(name = "currency_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyEntity {

  /**
   * identificador
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * nomenclatura de divisa
   */
  @Column(name = "currency")
  private String currency;

  /**
   * precio de la divisa comparada con 1 USD
   */
  @Column(name = "price")
  private BigDecimal price;

  /**
   * ultima hora de actualizacion
   */
  @Column(name = "called_time")
  private LocalDateTime calledTime;
}
