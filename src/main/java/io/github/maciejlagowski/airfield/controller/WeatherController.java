package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import io.github.maciejlagowski.airfield.model.service.weather.WeatherApiService;
import javassist.NotFoundException;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class WeatherController {

    private final WeatherApiService weatherApiService;

    @GetMapping("/weather")
    public WeatherDTO getWeatherOnDay(@RequestParam String date) {
        try {
            return weatherApiService.getWeatherOnDay(LocalDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT));
        } catch (NotFoundException e) {
            return new WeatherDTO(true);
        }
    }
}
