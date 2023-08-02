package dev.camilo.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Clase de configuracion para el mail
 */
@Configuration
@EnableConfigurationProperties
public class EmailConfig {

  /**
   * correo de donde se va a enviar
   */
  @Value("${mail.username}")
  private String mailUser;

  /**
   * contraseña de app
   */
  @Value("${mail.password}")
  private String mailPassword;

  /**
   * Configuracion de JavaMailSender para gmail
   * @return JavaMailSender
   */
  @Bean
  public JavaMailSender javaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);

    mailSender.setUsername(mailUser);
    mailSender.setPassword(mailPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", "smtp");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.debug", "true");

    return mailSender;
  }
}

