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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
            List<Role> roles = List.of(new Role(ERole.ROLE_ADMIN), new Role(ERole.ROLE_EMPLOYEE), new Role(ERole.ROLE_USER), new Role(ERole.ROLE_NOT_LOGGED));
            roleRepository.saveAll(roles);
            List<User> users = new LinkedList<>();
            Random rand = new Random();
            Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
                User user = User.builder()
                        .name(name)
                        .phoneNumber(Integer.toString(rand.nextInt(899999999) + 100000000))
                        .passwordHash(passwordEncoder.encode("abc"))
                        .roles(Set.of(roles.get(rand.nextInt(roles.size()))))
                        .build();
                userRepository.save(user);
                users.add(user);
                System.out.println(user);
            });
            User admin = User.builder()
                    .name("Admin")
                    .phoneNumber("666666666")
                    .passwordHash(passwordEncoder.encode(("admin")))
                    .roles(Set.of(roles.get(0)))
                    .build();
            userRepository.save(admin);
            LocalDate localDate = LocalDate.now();
            for (int i = 0; i < 5; i++) {
                int timeIterator = 7;
                LocalDate date = localDate.minusDays(i);
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
            System.err.println("DATA ADDING ENDED");
        };
    }
}
