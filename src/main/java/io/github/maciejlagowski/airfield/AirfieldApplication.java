package io.github.maciejlagowski.airfield;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AirfieldApplication {

    public static final boolean debug = false;

    public static void main(String[] args) {
        SpringApplication.run(AirfieldApplication.class, args);
    }
}
