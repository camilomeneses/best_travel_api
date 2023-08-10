package dev.camilo.demo.util.methods;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase con el metodo encargado de escribir las notificaciones con el
 * @Notify
 */
public class NotificationWriter {

  /**
   * Metodo para escribir en un archivo dado el path el texto del parametro,
   * escribe las notificaciones en el archivo txt
   * @param text String
   * @param path String
   * @throws IOException Exception
   */
  public static void writeNotification(String text, String path) throws IOException{
    var fileWriter = new FileWriter(path,true);
    var bufferedWrite = new BufferedWriter(fileWriter);
    try (fileWriter; bufferedWrite){
      bufferedWrite.write(text);
      bufferedWrite.newLine();
    }
  }
}
