package io.github.maciejlagowski.airfield.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDTO {

    private LocalDateTime dateTime;
    private LocalDateTime sunrise;
    private LocalDateTime sunset;

    //TEMP
    private Double dayTemp;
    private Double minTemp;
    private Double maxTemp;
    private Double nightTemp;
    private Double eveningTemp;
    private Double morningTemp;
    private Double feelsLikeDayTemp;
    private Double feelsLikeNightTemp;
    private Double feelsLikeEveningTemp;
    private Double feelsLikeMorningTemp;

    private Long pressure;
    private Long humidity;
    private Double windSpeed;
    private Long windDeg;
    private String mainWeather;
    private String descriptionWeather;
    private Long clouds;
    private Double pop;
}
