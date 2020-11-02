package io.github.maciejlagowski.airfield;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ReservationType;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.Stream;

@SpringBootApplication
public class AirfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirfieldApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReservationRepository reservationRepository, UserRepository userRepository) {
        return args -> {
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, name.toLowerCase() + "@domain.com");
                userRepository.save(user);
            });
            userRepository.findAll().forEach(System.out::println);
            Stream.of(userRepository.findAll()).forEach(user -> {
                reservationRepository.save(new Reservation(
                        0L,
                        LocalDate.of(2020, 2, 2),
                        LocalTime.of(8, 0),
                        LocalTime.of(11, 0),
                        user.iterator().next(),
                        ReservationType.FLIGHT
                ));
            });
            reservationRepository.findAll().forEach(System.out::println);
        };
    }
}
