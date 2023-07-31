package dev.camilo.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Clase de Inicio a la aplicacion
 */
@SpringBootApplication
@Slf4j
public class DemoApplication {

  /**
   * Metodo de Inicio a la aplicacion
   * @param args String[]
   */
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

}

