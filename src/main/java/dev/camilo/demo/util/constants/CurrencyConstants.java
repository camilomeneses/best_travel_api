package dev.camilo.demo.util.constants;

/**
 * constantes de Currency
 */
public class CurrencyConstants {
  /**
   * parte del path con el apartado de configuracion de divisas
   */

  public static final String CURRENCY_PATH = "/exchangerates_data/latest";
  /**
   * parte del path de request que tiene la base de moneda configurada
   */
  public static final String BASE_CURRENCY_QUERY_PARAM = "&base={baseCurrency}";
  /**
   * parte del path de request que tiene las divisas a convertir configuradas
   */
  public static final String SYMBOL_CURRENCY_QUERY_PARAM = "?symbols={symbolsCurrency}";

  /**
   * verificacion cada quince minutos
   */
  public static final String SCHEDULED_VERIFICATION_TIME = "0 */15 * * * *";
}
