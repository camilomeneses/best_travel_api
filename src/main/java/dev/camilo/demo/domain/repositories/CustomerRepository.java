package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Customer;
import org.springframework.data.repository.CrudRepository;


public interface CustomerRepository extends CrudRepository<Customer,String> {
}
