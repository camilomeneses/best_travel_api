package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Tour;
import org.springframework.data.repository.CrudRepository;


public interface TourRepository extends CrudRepository<Tour,Long> {
}
