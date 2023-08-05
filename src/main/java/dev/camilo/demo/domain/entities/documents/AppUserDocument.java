package dev.camilo.demo.domain.entities.documents;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "app_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDocument {

  @Id
  private UUID id;
  private String dni;
  private boolean enabled;
  private String password;
  private Role role;
}
