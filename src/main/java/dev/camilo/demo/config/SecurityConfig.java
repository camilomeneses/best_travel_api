package dev.camilo.demo.config;

import dev.camilo.demo.util.constants.SecurityConstants;
import dev.camilo.demo.util.classes.JwtAuthenticationFilter;
import dev.camilo.demo.util.classes.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


  //inyeccion de dependencias
  private final UserDetailsService userDetailsService;

  private final AuthenticationConfiguration authenticationConfiguration;

  /*agregamos un constructor relacionando este UserDetailsService con la implementacion
   * en AppUserService loadUserByUsername donde traemos el usuario de la base de datos mongoDB*/
  public SecurityConfig(@Qualifier(value = "appUserService") UserDetailsService userDetailsService, AuthenticationConfiguration authenticationConfiguration) {
    this.userDetailsService = userDetailsService;
    this.authenticationConfiguration = authenticationConfiguration;
  }

  /*metodo para servidor de recursos*/

  /**
   * Metodo para servidor de autenticacion configura los paths y sus roles
   * para su authorizacion
   *
   * @param http HttpSecurity
   * @return SecurityFilterChain
   * @throws Exception AuthenticationException
   */
  @Bean
  SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
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
        .addFilter(
            new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager())
        )
        .addFilter(
            new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager())
        )
        .csrf(
            AbstractHttpConfigurer::disable
        )
        .sessionManagement(
            management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .build();
  }

  @Bean
  AuthenticationManager authenticationManager() throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
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
