package dev.camilo.demo.domain.entities.documents;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Document appUser para mongo db
 */
@Document(collection = "app_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AppUserDocument {

  /**
   * identificador
   */
  @Id
  private String id;
  /**
   * dni
   */
  private String dni;
  /**
   * nombre usuario
   */
  private String username;
  /**
   * usuario habilitado
   */
  private boolean enabled;
  /**
   * password
   */
  private String password;
  /**
   * objeto Role
   */
  private Role role;
}
