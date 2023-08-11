package dev.camilo.demo.aspects;

import dev.camilo.demo.util.annotations.Notify;
import dev.camilo.demo.util.classes.NotificationWriter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Implementacion de aspecto para la annotation de Notify
 */
@Component
@Aspect
public class NotifyAspect {

  /*@Before() - ejecutar antes
  @Around() - ejecutar durante
  @After() - ejecutar despues*/

  /**
   * Implementacion para annotation @Notity la cual intercepta los args del los metodos
   * de los services con paginacion y saca el LocalDateTime.now(), size y order para
   * llevar esta info a un string y escribirlo en un archivo de texto
   * @param joinPoint JoinPoint
   * @throws IOException Exception
   */
  @After(value = "@annotation(dev.camilo.demo.util.annotations.Notify)")
  public void notifyInFile(JoinPoint joinPoint) throws IOException {
    /*tomar args de method interceptado*/
    var args = joinPoint.getArgs();
    /*argumentos*/
    var size = args[1];
    var order = args[2] == null ? "NONE" : args[2];

    /*sacar de la firma la annotation para sacar su value default*/
    var signature = (MethodSignature)joinPoint.getSignature();
    var method = signature.getMethod();
    var annotation = method.getAnnotation(Notify.class);

    /*escribir el string que va para el notify.txt*/
    var text = String.format(LINE_FORMAT, LocalDateTime.now(),annotation.value(), size,order);

    NotificationWriter.writeNotification(text,PATH);
  }

  /**
   * String de mensaje para notify.txt
   */
  private static final String LINE_FORMAT = "At %s new request %s, with size page %s and order %s";

  /**
   * path para notify.txt
   */
  private static final String PATH = "files/notify.txt";
}
