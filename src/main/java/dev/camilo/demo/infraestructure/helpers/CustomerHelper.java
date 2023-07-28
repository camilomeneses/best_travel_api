package dev.camilo.demo.infraestructure.helpers;

import dev.camilo.demo.domain.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Helper para contar numero de fly,reservations y tours por cada customer
 */
@Transactional
@Component
@RequiredArgsConstructor
public class CustomerHelper {

  //repositorios inyectados
  private final CustomerRepository customerRepository;

  /**
   * Metodo para aumentar el contador de vuelos, reservations y tours
   * @param customerId String
   * @param type Class
   */
  public void increase(String customerId, Class<?> type){

    var customerToUpdate = this.customerRepository.findById(customerId).orElseThrow();
    switch (type.getSimpleName()){
      case "TourService" ->
          customerToUpdate.setTotalTours(customerToUpdate.getTotalTours() + 1);
      case "TicketService" ->
          customerToUpdate.setTotalFlights(customerToUpdate.getTotalFlights() + 1);
      case "ReservationService" ->
          customerToUpdate.setTotalLodgings(customerToUpdate.getTotalLodgings() + 1);
    }

    this.customerRepository.save(customerToUpdate);
  }
}
