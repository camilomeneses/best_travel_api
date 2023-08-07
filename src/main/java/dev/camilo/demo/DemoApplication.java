package dev.camilo.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase de Inicio a la aplicacion
 */
@SpringBootApplication
@EnableScheduling
public class DemoApplication {
  /**
   * Metodo de Inicio a la aplicacion
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

