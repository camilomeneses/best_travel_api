package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.TourEntity;
import org.springframework.data.repository.CrudRepository;


public interface TourRepository extends CrudRepository<TourEntity,Long> {
}
