package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import io.github.maciejlagowski.airfield.model.service.weather.WeatherApiService;
import javassist.NotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class WeatherController {

    private final WeatherApiService weatherApiService;

    @GetMapping("/weather")
    @ResponseStatus(HttpStatus.OK)
    public WeatherDTO getWeatherOnDay(@RequestParam String date) throws NotFoundException {
        return weatherApiService.getWeatherOnDay(LocalDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT));
    }
}
