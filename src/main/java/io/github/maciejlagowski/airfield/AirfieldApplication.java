package io.github.maciejlagowski.airfield;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.Status;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class AirfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirfieldApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReservationRepository reservationRepository, UserRepository userRepository) {
        return args -> {
            List<User> users = new LinkedList<>();
            Random rand = new Random();
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = new User(name, Integer.toString((rand.nextInt(899999999) + 100000000)));
                userRepository.save(user);
                users.add(user);
            });
            LocalDate localDate = LocalDate.now();
            for(int i = 0; i < 5; i++) {
                int timeIterator = 7;
                LocalDate date = localDate.minusDays(i);
                for (User user : users) {
                    reservationRepository.save(new Reservation(
                            0L,
                            date,
                            LocalTime.of(timeIterator, 0),
                            LocalTime.of(++timeIterator, 0),
                            user,
                            ReservationType.rand(),
                            Status.rand()
                    ));
                    timeIterator += new Random().nextInt(3);
                }
            }
            reservationRepository.findAll().forEach(System.out::println);
        };
    }
}
