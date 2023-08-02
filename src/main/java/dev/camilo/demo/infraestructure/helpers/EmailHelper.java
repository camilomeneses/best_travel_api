package dev.camilo.demo.infraestructure.helpers;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Helper para construir el mail de successful registration
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailHelper {

  /**
   * correo de donde se envia
   */
  @Value(value = "${mail.username}")
  private String mailAddress;

  /**
   * Mail Sender con la configuracion de Gmail cargada
   */
  private final JavaMailSender mailSender;

  /**
   * Metodo para enviar un correo, primero lee la plantilla html y remplaza
   * con las variables que vienen desde el service, despues construye el mail
   * y lo envia
   * @param to String
   * @param name String
   * @param product String
   */
  public void sendMail(String to,String name, String product){
    MimeMessage message = mailSender.createMimeMessage();
    /*leer el template del html*/
    String htmlContent = this.readHtmlTemplate(name,product);

    try {
      /*correo de quien envia el mensaje*/
      message.setFrom(new InternetAddress(mailAddress));
      /*correo de quien recibe el mensaje*/
      message.setRecipients(MimeMessage.RecipientType.TO, to);
      /*correo subject*/
      message.setSubject(String.format("Successful %s registration",product));
      /*contenido del mensaje*/
      message.setContent(htmlContent, MediaType.TEXT_HTML_VALUE);

      /*enviar el mensaje*/
      mailSender.send(message);

    } catch (MessagingException e) {
        log.error("Error to send mail", e);
    }
  }

  /**
   * Metodo para leer el template html linea a linea y poder remplazar
   * las variables de product y name
   * @param name String
   * @param product String
   * @return String
   */
  private String readHtmlTemplate(String name, String product){
    try(var lines = Files.lines(TEMPLATE_PATH)){
      var html = lines.collect(Collectors.joining());
      return html.replace("{name}",name).replace("{product}", product);
    }catch (IOException e){
      log.error("Cant read html template", e);
      throw new RuntimeException();
    }
  }

  /**
   * Constante donde se encuentra la ruta de el template de successful registration
   */
  private Path TEMPLATE_PATH = Paths.get("src/main/resources/email/email_template.html");
}
