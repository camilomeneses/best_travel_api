package dev.camilo.demo;

import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase de Inicio a la aplicacion
 */
@SpringBootApplication
@EnableScheduling
public class DemoApplication implements CommandLineRunner {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private AppUserRepository appUserRepository;
  /**
   * Metodo de Inicio a la aplicacion
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    this.appUserRepository.findAll()
        .forEach(user -> System.out.println(user.getUsername() + " - "+ this.bCryptPasswordEncoder.encode(user.getPassword())));
  }
}

