package io.github.maciejlagowski.airfield;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.Role;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.repository.RoleRepository;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Stream;

@SpringBootApplication
//@EnableSwagger2
public class AirfieldApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirfieldApplication.class, args);
    }

    public final static SecretKey keyForHS = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Bean
    CommandLineRunner init(ReservationRepository reservationRepository, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Set<Role> roles = new HashSet<>();
            Role role = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(role);
            roles.add(role);
            List<User> users = new LinkedList<>();
            Random rand = new Random();
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = User.builder()
                        .name(name)
                        .phoneNumber(Integer.toString(rand.nextInt(899999999) + 100000000))
                        .passwordHash(passwordEncoder.encode("abc"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                users.add(user);
            });
            LocalDate localDate = LocalDate.now();
            for (int i = 0; i < 5; i++) {
                int timeIterator = 7;
                LocalDate date = localDate.minusDays(i);
                for (User user : users) {
                    reservationRepository.save(new Reservation(
                            0L,
                            date,
                            LocalTime.of(timeIterator, 0),
                            LocalTime.of(++timeIterator, 0),
                            user,
                            EStatus.rand(),
                            EReservationType.rand()
                    ));
                    timeIterator += new Random().nextInt(3);
                }
            }
            reservationRepository.findAll().forEach(System.out::println);
        };
    }
}
