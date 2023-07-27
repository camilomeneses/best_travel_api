package dev.camilo.demo.util;

import java.time.LocalDateTime;
import java.util.Random;

public class BestTravelUtil {
  private static final Random random = new Random();

  /*generar hora proxima*/
  public static LocalDateTime getRandomSoon(){
    var randomHours = random.nextInt(5-2)+2;
    var now = LocalDateTime.now();
    return now.plusHours(randomHours);
  }

  /*generar hora lejana*/
  public static LocalDateTime getRandomLatter(){
    var randomHours = random.nextInt(12-6)+6;
    var now = LocalDateTime.now();
    return now.plusHours(randomHours);
  }
}
