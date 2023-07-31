package dev.camilo.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase de Inicio a la aplicacion
 */
@SpringBootApplication
@EnableScheduling
@Slf4j
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

