package dev.camilo.demo.util.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.camilo.demo.domain.entities.documents.AppUserDocument;
import dev.camilo.demo.domain.repositories.mongo.AppUserRepository;
import dev.camilo.demo.util.constants.SecurityConstants;
import dev.camilo.demo.util.enums.Documents;
import dev.camilo.demo.util.exceptions.UsernameNotFoundException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    AppUserDocument user;
    String username;
    String password;

    try {
      user = new ObjectMapper().readValue(request.getInputStream(), AppUserDocument.class);
/*      var userDb = this.appUserRepository.findByUsername(user.getUsername())
          .orElseThrow(() -> new UsernameNotFoundException(Documents.app_user.name()));
      username = userDb.getUsername();*/
      username = user.getUsername();
      password = user.getPassword();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    return authenticationManager.authenticate(authToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    String username = ((User) authResult.getPrincipal()).getUsername();
    Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
    boolean isAdmin = roles.stream().anyMatch(rol -> rol.getAuthority().equals(SecurityConstants.ADMIN_ROLE));


    /*crear token firmado y agregado los claims*/
    String token = Jwts.builder()
        .setSubject(username)
        .setClaims(Map.of(
            "username", username,
            "authorities", new ObjectMapper().writeValueAsString(roles),
            "author", SecurityConstants.APPLICATION_OWNER,
            "isAdmin", isAdmin
        ))
        .signWith(SecurityConstants.SECRET_KEY)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora adicional
        .compact();

    response.addHeader(SecurityConstants.HEADER_AUTHORIZATION, String.format("%s%s", SecurityConstants.PREFIX_TOKEN, token));

    Map<String, Object> body = new HashMap<>();

    body.put("token", token);
    body.put("message", String.format("Hola %s has iniciado session con éxito!", username));
    body.put("username", username);

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setStatus(200);
    response.setContentType("application/json");
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "Error en la autenticacion username o password incorrecto");

    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    response.setStatus(401);
    response.setContentType("application/json");
  }
}
