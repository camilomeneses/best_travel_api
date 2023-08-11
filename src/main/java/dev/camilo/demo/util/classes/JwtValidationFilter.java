package dev.camilo.demo.util.classes;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.camilo.demo.util.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JwtValidationFilter extends BasicAuthenticationFilter {
  public JwtValidationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

    String header = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
    if (header == null || !header.startsWith(SecurityConstants.PREFIX_TOKEN)) {
      chain.doFilter(request, response);
      return;
    }

    /*sacando token para validacion*/
    String token = header.replace(SecurityConstants.PREFIX_TOKEN, "");

    try {

      /*validar el token sacando los claims*/
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(SecurityConstants.SECRET_KEY)
          .build()
          .parseClaimsJws(token)
          .getBody();

      String username = claims.get("username").toString();
      Object authoritiesClaims = claims.get("authorities");

      Collection<? extends GrantedAuthority> authorities = Arrays
          .asList(new ObjectMapper()
              .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
              .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              username,
              null,
              authorities);

      /*autenticacion*/
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    } catch (JwtException e) {
      Map<String, String> body = new HashMap<>();
      body.put("message", e.getMessage());

      response.getWriter().write(new ObjectMapper().writeValueAsString(body));
      response.setStatus(403);
      response.setContentType("application/json");
    }
  }
}
