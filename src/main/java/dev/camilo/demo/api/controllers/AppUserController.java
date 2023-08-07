package dev.camilo.demo.api.controllers;

import dev.camilo.demo.infraestructure.abstract_services.ModifyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para respuestas JSON y XML para fly
 */
@Tag(name = "User")
@RestController
@RequestMapping(path = "user")
@RequiredArgsConstructor
public class AppUserController {
  //servicios inyectados
  private final ModifyUserService userService;

  @Operation(
      summary = "Habilitar/Desabilitar Usuario",
      description = "Cambiar la propiedad enabled a true o false"
  )
  @PatchMapping(path = "enabled-or-disabled")
  public ResponseEntity<Map<String,Boolean>> enabledOrDisabledJson(@RequestParam String username){
    return ResponseEntity.ok(this.userService.enabled(username));
  }

  @Operation(
      summary = "Agregar role a user",
      description = "Agregar role a usuario"
  )
  @PatchMapping(path = "add-role")
  public ResponseEntity<Map<String, List<String>>> addRole(
      @RequestParam String username,
      @RequestParam String role
  ){
    return ResponseEntity.ok(this.userService.addRole(username,role));
  }

  @Operation(
      summary = "Remove role a user",
      description = "Remove role a usuario"
  )
  @PatchMapping(path = "remove-role")
  public ResponseEntity<Map<String, List<String>>> removeRole(
      @RequestParam String username,
      @RequestParam String role
  ){
    return ResponseEntity.ok(this.userService.removeRole(username,role));
  }
}
