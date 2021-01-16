package io.github.maciejlagowski.airfield.model.service.weather;

import io.github.maciejlagowski.airfield.model.dto.WeatherAlertDTO;
import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import io.github.maciejlagowski.airfield.model.enumeration.EAlertImpact;
import org.springframework.stereotype.Service;

@Service
public class WeatherDTOService {

    public WeatherDTO constructFromHourly(Hourly hourly) {
        return WeatherDTO.builder()
                .dateTime(hourly.getDateTime())
                .dayTemp(hourly.getTemp())
                .feelsLikeDayTemp(hourly.getFeelsLike())
                .pressure(hourly.getPressure())
                .humidity(hourly.getHumidity())
                .windSpeed(hourly.getWindSpeed())
                .windDeg(hourly.getWindDeg())
                .mainWeather(hourly.getWeather().getMain())
                .descriptionWeather(hourly.getWeather().getDescription())
                .clouds(hourly.getClouds())
                .pop(hourly.getPop() * 100)
                .build();
    }

    public WeatherDTO constructFromDaily(Daily daily) {
        WeatherDTO weatherDTO = constructFromHourly(daily);
        weatherDTO.setSunrise(daily.getSunrise());
        weatherDTO.setSunset(daily.getSunset());
        weatherDTO.setMinTemp(daily.getTemperature().getMin());
        weatherDTO.setMaxTemp(daily.getTemperature().getMax());
        weatherDTO.setNightTemp(daily.getTemperature().getNight());
        weatherDTO.setEveningTemp(daily.getTemperature().getEve());
        weatherDTO.setMorningTemp(daily.getTemperature().getMorn());
        weatherDTO.setFeelsLikeDayTemp(daily.getFeelsLikeTemp().getDay());
        weatherDTO.setFeelsLikeNightTemp(daily.getFeelsLikeTemp().getNight());
        weatherDTO.setFeelsLikeEveningTemp(daily.getFeelsLikeTemp().getEve());
        weatherDTO.setFeelsLikeMorningTemp(daily.getFeelsLikeTemp().getMorn());
        weatherDTO.setDayTemp(daily.getTemperature().getDay());
        return weatherDTO;
    }

    public WeatherAlertDTO constructAlert(Alert alert) {
        return WeatherAlertDTO.builder()
                .senderName(alert.getSenderName())
                .event(alert.getEvent())
                .description(alert.getDescription())
                .startTime(alert.getStartTime())
                .endTime(alert.getEndTime())
                .alertImpact(EAlertImpact.getImpactFromString(alert.getEvent()))
                .build();
    }
}
