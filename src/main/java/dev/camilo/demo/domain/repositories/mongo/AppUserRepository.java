package dev.camilo.demo.domain.repositories.mongo;

import dev.camilo.demo.domain.entities.documents.AppUserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AppUserRepository extends MongoRepository<AppUserDocument, UUID> {
}
