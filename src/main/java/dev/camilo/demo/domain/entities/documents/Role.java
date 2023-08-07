package dev.camilo.demo.domain.entities.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

/**
 * Modelado de Objeto interno de Role en AppUser
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Field(name = "granted_authorities")
  private Set<String> grantedAuthorities;
}
