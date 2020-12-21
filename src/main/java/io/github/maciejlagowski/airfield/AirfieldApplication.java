package io.github.maciejlagowski.airfield;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
@EnableScheduling
public class AirfieldApplication {

    public static final boolean debug = false;

    public static void main(String[] args) {
        SpringApplication.run(AirfieldApplication.class, args);
    }

    @Bean
    CommandLineRunner init(ReservationRepository reservationRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            List<User> users = new LinkedList<>();
            Random rand = new Random();
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = User.builder()
                        .email(name + "@mail.com")
                        .name(name)
                        .phoneNumber(Integer.toString(rand.nextInt(899999999) + 100000000))
                        .passwordHash(passwordEncoder.encode("abc"))
                        .role(ERole.rand())
                        .build();
                userRepository.save(user);
                users.add(user);
                System.out.println(user);
            });
            User admin = User.builder()
                    .email("admin@admin.com")
                    .name("Admin")
                    .phoneNumber("666666666")
                    .passwordHash(passwordEncoder.encode(("admin")))
                    .role(ERole.ROLE_ADMIN)
                    .build();
            userRepository.save(admin);
            LocalDate localDate = LocalDate.now();
            for (int i = 0; i < 7; i++) {
                int timeIterator = 7;
                LocalDate date = localDate.plusDays(2).minusDays(i);
                for (User user : users) {
                    reservationRepository.save(Reservation.builder()
                            .date(date)
                            .startTime(LocalTime.of(timeIterator, 0))
                            .endTime(LocalTime.of(++timeIterator, 0))
                            .user(user)
                            .status(EStatus.rand())
                            .reservationType(EReservationType.rand())
                            .build());
                    timeIterator += new Random().nextInt(3);
                }
            }
            System.err.println("------------ DATA ADD DONE ------------");
        };
    }
}
