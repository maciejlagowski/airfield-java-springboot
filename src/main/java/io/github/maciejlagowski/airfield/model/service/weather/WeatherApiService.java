package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maciejlagowski.airfield.exception.JsonMappingException;
import io.github.maciejlagowski.airfield.model.dto.WeatherAlertDTO;
import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

    private final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=52.229676&lon=21.012229&exclude=current,minutely&units=metric&appid=";
    private final RestTemplate restTemplate;
    private final WeatherDTOService dtoService;
    @Value("${weatherApiService.apiKey}")
    private String apiKey;
    private WeatherApiResponse weather;

    @Scheduled(fixedRate = 3600000)
    public void updateWeather() {
        String apiResponse = restTemplate.getForObject(url + apiKey, String.class);
        try {
            weather = new ObjectMapper().readValue(apiResponse, WeatherApiResponse.class);
        } catch (Exception e) {
            throw new JsonMappingException(e.getMessage());
        }
    }

    public WeatherDTO getWeatherOnDay(LocalDateTime date) throws NotFoundException {
        return dtoService.constructFromDaily(
                weather.getDaily(date)
        );
    }

    public List<WeatherAlertDTO> getAlerts() {
        return weather.getAlerts().stream().map(dtoService::constructAlert).collect(Collectors.toList());
    }

    public WeatherDTO getWeatherOnHour(LocalDateTime dateTime) {
        return dtoService.constructFromHourly(
                weather.getHourly(dateTime)
        );
    }
}
