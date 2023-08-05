package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.jpa.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Acceso a datos a la tabla de customer
 */
public interface CustomerRepository extends CrudRepository<CustomerEntity,String> {
}
