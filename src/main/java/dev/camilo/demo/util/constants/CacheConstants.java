package dev.camilo.demo.util.constants;

/**
 * Constantes de Cache
 */
public class CacheConstants {
  /**
   * nombre key de flights
   */
  public static final String FLY_CACHE_NAME = "flights";
  /**
   * nombre key de hotels
   */
  public static final String HOTEL_CACHE_NAME = "hotels";
  /**
   * limpiar cache cada dia a las 12 AM
   */
  public static final String SCHEDULED_RESET_CACHE = "0 0 0 * * ?";
}