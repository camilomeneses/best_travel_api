package dev.camilo.demo;

import dev.camilo.demo.domain.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
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

		var fly = flyRepository.findByTicketId(UUID.fromString("12345678-1234-5678-2236-567812345678")).get();
		System.out.println(fly);
	}
}
