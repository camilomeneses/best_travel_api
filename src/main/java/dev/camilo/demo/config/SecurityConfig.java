package dev.camilo.demo.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import dev.camilo.demo.util.constants.SecurityConstants;
import dev.camilo.demo.util.methods.RSAkeys;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Configuration
public class SecurityConfig {

  /**
   * id de frontend cliente
   */
  @Value("${app.client.id}")
  private String clientId;
  /**
   * secret de frontend cliente
   */
  @Value("${app.client.secret}")
  /**
   * scope de frontend cliente
   */
  private String clientSecret;
  @Value("${app.client-scope-read}")
  /**
   * scope read de frontend cliente
   */
  private String scopeRead;
  /**
   * scope write de frontend cliente
   */
  @Value("${app.client-scope-write}")
  /**
   * redireccion 1 aplicacion cliente
   */
  private String scopeWrite;
  @Value("${app.client-redirect-debugger}")
  /**
   * redireccion 2 aplicacion cliente
   */
  private String redirectUri1;
  @Value("${app.client-redirect-spring-doc}")
  private String redirectUri2;


  //inyeccion de dependencias
  private final UserDetailsService userDetailsService;

  /*agregamos un constructor relacionando este UserDetailsService con la implementacion
   * en AppUserService loadUserByUsername donde traemos el usuario de la base de datos mongoDB*/
  public SecurityConfig(@Qualifier(value = "appUserService") UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  /*metodo para servidor de autenticacion*/

  /**
   * Metodo para servidor de autenticacion
   *
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception AuthenticationException
   */
  @Bean
  @Order(1)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    /*agregando seguridad por defecto (todas las request necesitan authenticacion)*/
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    /*configuracion por defecto de ServerConfig*/
    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .oidc(Customizer.withDefaults());

    /*primer filtro de seguridad, redireccion a login*/
    http.exceptionHandling(e ->
        e.authenticationEntryPoint(
            new LoginUrlAuthenticationEntryPoint(SecurityConstants.LOGIN_RESOURCE)));

    return http.build();
  }

  /*metodo para servidor de recursos*/

  /**
   * Metodo para servidor de autenticacion configura los paths y sus roles
   * para su authorizacion
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception AuthenticationException
   */
  @Bean
  @Order(2)
  public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    http
        .formLogin()
        .and()
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(SecurityConstants.PUBLIC_RESOURCES)
                .permitAll()
        )
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(SecurityConstants.USER_RESOURCES)
                .authenticated()
        )
        .authorizeHttpRequests(
            authorize -> authorize
                .requestMatchers(SecurityConstants.ADMIN_RESOURCES)
                .hasAuthority(SecurityConstants.ADMIN_ROLE)
        )
        .oauth2ResourceServer()
        .jwt();
    return http.build();
  }


  /**
   * Metodo del proveedor de Authenticacion para los usuarios,
   * autentica a los usuarios con el backend
   *
   * @param encoder BCryptPasswordEncoder
   * @return AuthenticationProvider
   */
  @Bean
  public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder) {
    var authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(encoder);
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }

  /**
   * Metodo de registro de clientes (frontend) para autenticacion de cliente con
   * el backend
   *
   * @param encoder BCryptPasswordEncoder
   * @return RegisteredClientRepository
   */
  @Bean
  public RegisteredClientRepository registeredClientRepository(BCryptPasswordEncoder encoder) {
    var registeredClient = RegisteredClient
        .withId(UUID.randomUUID().toString())
        .clientId(clientId)
        .clientSecret(encoder.encode(clientSecret))
        .scope(scopeRead)
        .scope(scopeWrite)
        .redirectUri(redirectUri1) /*simulacion front 1*/
        .redirectUri(redirectUri2)/*simulacion front 2*/
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .build();

    /*cliente en memoria (se puede registrar en base de datos)*/
    return new InMemoryRegisteredClientRepository(registeredClient);
  }

  // sin implementacion

  /**
   * Metodo para crear servidor de autorización OAuth 2.0
   *
   * @return AuthorizationServerSettings
   */
  @Bean
  public AuthorizationServerSettings authorizationServerSettings() {
    return AuthorizationServerSettings.builder().build();
  }

  // sin implementacion

  /**
   * Metodo para decodificar los tokens JWT
   *
   * @param jwkSource JWKSource
   * @return JwtDecoder
   */
  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }


  /**
   * Metodo para decodificar (firmar) los tokens JWT
   * @return JWKSource
   */
  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    /*generando llave publica y privada*/
    var rsaKey = RSAkeys.generateKeys();
    var jwtSet = new JWKSet(rsaKey);
    return ((jwkSelector, securityContext) -> jwkSelector.select(jwtSet));
  }

  /**
   * Metodo para configurar las settings del token
   *
   * @return TokenSettings
   */
  @Bean
  public TokenSettings tokenSettings() {
    return TokenSettings.builder()
        .refreshTokenTimeToLive(Duration.ofHours(2))
        .build();
  }

  /**
   * Personalizar el prefijo de la authoritie de Granted Autorities
   *
   * @return JwtGrantedAuthoritiesConverter
   */
  @Bean
  public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
    var converter = new JwtGrantedAuthoritiesConverter();
    /*quitando prefijo*/
    converter.setAuthorityPrefix("");
    return converter;
  }

  /**
   * Metodo para personalizar el prefijo de autoritie en la
   * autenticación segun lo que tenga el Granted Authorities
   *
   * @param jwtGrantedAuthoritiesConverter JwtGrantedAuthoritiesConverter
   * @return JwtAuthenticationConverter
   */
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
    var converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
    return converter;
  }

  /**
   * Metodo para colocar claims personalizados en el payload del JWT
   *
   * @return OAuth2TokenCustomizer
   */
  @Bean
  public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
    return context -> {
      if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
        context.getClaims().claims(claim -> {
          claim.putAll(Map.of(
              "owner", SecurityConstants.APPLICATION_OWNER,
              "date_request", LocalDateTime.now().toString()
          ));
        });
      }
    };
  }


  //metodos privados

  /**
   * Metodo para generar un BCryptPassword (base64)
   *
   * @return BCryptPasswordEncoder
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
