package dev.camilo.demo;

import dev.camilo.demo.domain.entities.Reservation;
import dev.camilo.demo.domain.entities.Ticket;
import dev.camilo.demo.domain.entities.Tour;
import dev.camilo.demo.domain.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DemoApplication implements CommandLineRunner {

	private final HotelRepository hotelRepository;
	private  final FlyRepository flyRepository;
	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final TourRepository tourRepository;
	private final CustomerRepository customerRepository;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	/*	var fly = flyRepository.findById(14L).get();
		var hotel = hotelRepository.findById(7L).get();
		var ticket = ticketRepository
				.findById(UUID.fromString("32345678-1234-5678-4234-567812345678")).get();
		var reservation = reservationRepository
				.findById(UUID.fromString("22345678-1234-5678-1234-567812345678")).get();
		var customer = customerRepository.findById("BBMB771012HMCRR022").get();

		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));

		log.info(String.valueOf(ticket));
		log.info(String.valueOf(reservation));
		log.info(String.valueOf(customer));*/

		/*this.flyRepository.selectLessPrice(BigDecimal.valueOf(20)).forEach(System.out::println);*/
		/*this.flyRepository.selectBetweenPrice(BigDecimal.valueOf(10),BigDecimal.valueOf(15)).forEach(System.out::println);*/
		/*this.flyRepository.selectOriginDestiny("Grecia","Mexico").forEach(System.out::println);*/

		/*var fly = flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
		System.out.println(fly);*/

		//JPQL method names
		/*hotelRepository.findByPriceLessThan(BigDecimal.valueOf(100)).forEach(System.out::println);*/
		/*hotelRepository.findByPriceBetween(BigDecimal.valueOf(100),BigDecimal.valueOf(200))
				.forEach(System.out::println);*/
		/*hotelRepository.findByRatingGreaterThan(3).forEach(System.out::println);*/

		//JPQL join method names
		/*var hotel = hotelRepository.findByReservationsId(UUID.fromString("12345678-1234-5678-1234-567812345678")).get();
		System.out.println(hotel);*/

		//customer
		var customer = customerRepository.findById("GOTW771012HMRGR087").orElseThrow();
		log.info("Client name: " + customer.getFullName());

		//hotel
		var hotel = hotelRepository.findById(3L).orElseThrow();
		log.info("Hotel name: " + hotel.getName());

		//fly
		var fly = flyRepository.findById(11L).orElseThrow();
		log.info("Fly: " + fly.getOriginName() + " - " + fly.getDestinyName());

		//creacion de tour con customer con patron builder
		var tour = Tour.builder()
				.customer(customer)
				.build();

		//creacion de ticket con patron builder
		var ticket = Ticket.builder()
				.id(UUID.randomUUID())
				.price(fly.getPrice().multiply(BigDecimal.TEN))
				.arrivalDate(LocalDate.now())
				.departureDate(LocalDate.now())
				.purchaseDate(LocalDate.now())
				.customer(customer)
				.tour(tour)
				.fly(fly)
				.build();

		//creacion de reservacion con patron builder
		var reservation = Reservation.builder()
				.id(UUID.randomUUID())
				.dateTimeReservation(LocalDateTime.now())
				.dateEnd(LocalDate.now().plusDays(2))
				.dateStart(LocalDate.now().plusDays(1))
				.hotel(hotel)
				.customer(customer)
				.tour(tour)
				.totalDays(1)
				.price(hotel.getPrice().multiply(BigDecimal.TEN))
				.build();

		System.out.println("---- Saving ----");
		tour.addReservation(reservation);
		tour.updateReservations();

		tour.addTicket(ticket);
		tour.updateTickets();
		var tourSaved = this.tourRepository.save(tour);
		System.out.println("---- Deleting ----");
		Thread.sleep(8000);
		this.tourRepository.deleteById(tourSaved.getId());

	}
}
