package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<CustomerEntity,String> {
}
