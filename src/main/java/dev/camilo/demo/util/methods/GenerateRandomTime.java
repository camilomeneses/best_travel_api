package dev.camilo.demo.util.methods;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Utilidades generales que pueden usarse a nivel global en el app
 */
public class GenerateRandomTime {
  private static final Random random = new Random();

  /*generar hora proxima*/

  /**
   * Metodo para generar una hora random entre 0 y 3 horas
   * @return LocalDateTime
   */
  public static LocalDateTime getRandomSoon(){
    var randomHours = random.nextInt(5-2)+2;
    var now = LocalDateTime.now();
    return now.plusHours(randomHours);
  }

  /*generar hora lejana*/

  /**
   * Metodo para generar una hora random entre 0 y 6 horas
   * @return LocalDateTime
   */
  public static LocalDateTime getRandomLatter(){
    var randomHours = random.nextInt(12-6)+6;
    var now = LocalDateTime.now();
    return now.plusHours(randomHours);
  }
}
