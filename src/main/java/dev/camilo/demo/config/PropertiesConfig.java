package dev.camilo.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/*PropertySource trae los valores del .properties de api_currency con ruta
* relativa*/
/**
 * conexion de api_currency.properties con la configuracion de nuestra api
 */
@Configuration
@PropertySource(value = "classpath:configs/api_currency.properties")
@PropertySource(value = "classpath:configs/mail_sender.properties")
public class PropertiesConfig {
}
