package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.WeatherAlertDTO;
import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import io.github.maciejlagowski.airfield.model.service.weather.WeatherApiService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherApiService weatherApiService;

    @GetMapping("/weather")
    @ResponseStatus(HttpStatus.OK)
    public WeatherDTO getWeatherOnDay(@RequestParam String date) throws NotFoundException {
        return weatherApiService.getWeatherOnDay(LocalDateTime.of(LocalDate.parse(date), LocalTime.MIDNIGHT));
    }

    @GetMapping("/weather/alerts")
    @ResponseStatus(HttpStatus.OK)
    public List<WeatherAlertDTO> getAlerts() {
        return weatherApiService.getAlerts();
    }
}
