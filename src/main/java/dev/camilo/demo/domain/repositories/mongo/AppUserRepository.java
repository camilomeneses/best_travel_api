package dev.camilo.demo.domain.repositories.mongo;

import dev.camilo.demo.domain.entities.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Acceso a datos a el documento de AppUser en mongoDB
 */
public interface AppUserRepository extends MongoRepository<AppUserDocument, String> {

  /**
   * Metodo para buscar usuario por nombre
   * @param username String
   * @return Optional
   */
  Optional<AppUserDocument> findByUsername(String username);

  /**
   * Metodo para buscar usuario por Dni
   * @param userDni String
   * @return Optional
   */
  Optional<AppUserDocument> findByDni(String userDni);
}
