package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Fly;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FlyRepository extends JpaRepository<Fly,Long> {
}
