package dev.camilo.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * configuracion de WebClient con ejemplo de 2 WebClient diferenciados por
 * nombre de Bean diferente para ser llamados con @Qualifier
 */
@Configuration
public class WebClientConfig {

  /**
   * base de Url para api
   */
  @Value(value = "${api.base.url}")
  private String baseUrl;
  /**
   * key de acceso a api
   */
  @Value(value = "${api.api-key}")
  private String apiKey;
  /**
   * nombre de header para apiKey
   */
  @Value(value = "${api.api-key.header}")
  private String apiKeyHeader;

  /**
   * WebClient para currency
   * @return WebClient
   */
  @Bean(name = "currency")
  public WebClient webClient(){
    return WebClient
        .builder()
        .baseUrl(baseUrl)
        .defaultHeader(apiKeyHeader,apiKey)
        .build();
  }
}
