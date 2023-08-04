package dev.camilo.demo.config;

import dev.camilo.demo.util.constants.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;

/**
 * Configuracion de servidor redis usando las librerias de redisson
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig {

  /**
   * redis server address
   */
  @Value(value = "${cache.redis.address}")
  private String serverAddress;
  /**
   * redis server password
   */
  @Value(value = "${cache.redis.password}")
  private String serverPassword;

  /**
   * cliente de redis
   * @return RedissonClient
   */
  @Bean
  public RedissonClient redissonClient() {
    /*creacion de configuracion*/
    var config = new Config();

    /*para produccion usar ClusterServer si es local SingleServer*/
    config.useSingleServer()
        .setAddress(serverAddress)
        .setPassword(serverPassword);

    return Redisson.create(config);
  }

  /**
   * CacheManager de Spring como este metodo requiere de otro Bean para trabajar se debe
   * inicializar por inyeccion de dependencias con @Bean, usando asi el resultado de
   * redissonClient()
   * @param redissonClient RedissonClient
   * @return CacheManager
   */
  @Bean
  @Autowired
  public CacheManager cacheManager(RedissonClient redissonClient) {
    var configs = Map.of(
        CacheConstants.FLY_CACHE_NAME, new CacheConfig(),
        CacheConstants.HOTEL_CACHE_NAME, new CacheConfig()
    );
    return new RedissonSpringCacheManager(redissonClient, configs);
  }

  /**
   * Metodo para limpiar el cache cada día a las 12am en otro hilo
   */
  @Async
  @Scheduled(cron = CacheConstants.SCHEDULED_RESET_CACHE)
  @CacheEvict(cacheNames = {
      CacheConstants.HOTEL_CACHE_NAME,
      CacheConstants.FLY_CACHE_NAME
  },
  allEntries = true
  )
  public void deleteCache(){
    log.info(" --- --- Clean cache --- --- ");
  }
}
