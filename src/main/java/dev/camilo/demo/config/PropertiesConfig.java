package dev.camilo.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/*PropertySource trae los valores del .properties de api_currency con ruta
 * relativa*/

/**
 * conexion de los archivos .properties con la configuracion de nuestra api
 */
@Configuration
@PropertySources({
    @PropertySource(value = "classpath:configs/api_currency.properties"),
    @PropertySource(value = "classpath:configs/mail_sender.properties"),
    @PropertySource(value = "classpath:configs/redis.properties"),
    @PropertySource(value = "classpath:configs/client_security.properties")
})
public class PropertiesConfig {
}
