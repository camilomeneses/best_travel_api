package dev.camilo.demo.domain.repositories;

import dev.camilo.demo.domain.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
