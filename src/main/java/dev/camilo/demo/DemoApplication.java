package dev.camilo.demo;

import dev.camilo.demo.domain.entities.Fly;
import dev.camilo.demo.domain.entities.Hotel;
import dev.camilo.demo.domain.repositories.FlyRepository;
import dev.camilo.demo.domain.repositories.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class DemoApplication implements CommandLineRunner {

	private final HotelRepository hotelRepository;

	private  final FlyRepository flyRepository;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var fly = flyRepository.findById(14L).get();
		var hotel = hotelRepository.findById(7L).get();

		log.info(String.valueOf(fly));
		log.info(String.valueOf(hotel));
	}
}
