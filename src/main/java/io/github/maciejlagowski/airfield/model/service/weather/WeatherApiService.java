package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maciejlagowski.airfield.model.dto.WeatherDTO;
import io.github.maciejlagowski.airfield.model.helper.DateConverter;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.zone.ZoneRulesException;

@Service
public class WeatherApiService {

    private final String url = "https://api.openweathermap.org/data/2.5/onecall?lat=52.229676&lon=21.012229&exclude=current,minutely&units=metric&appid=";
    private final RestTemplate restTemplate = new RestTemplate();
    private final WeatherDTOService dtoService;
    @Value("${weatherApiService.apiKey}")
    private String apiKey;
    private WeatherApiResponse weather;

    public WeatherApiService(WeatherDTOService dtoService) {
        this.dtoService = dtoService;
    }

    @Bean
    public void updateWeather() throws JsonProcessingException { // TODO switch to real API, updating every hour?
//        String result = restTemplate.getForObject(url + apiKey, String.class);
        String result = getFakeApi();
        ObjectMapper objectMapper = new ObjectMapper();
        weather = objectMapper.readValue(result, WeatherApiResponse.class);
        if (weather.getZone().equals(ZoneId.systemDefault())) {
            System.out.println("Weather updated");
        } else {
            throw new ZoneRulesException("Timezones from api and system are different");
        }
    }

    public WeatherDTO getWeatherOnDay(LocalDateTime date) throws NotFoundException {
        return dtoService.constructFromDaily(
                weather.getDaily(date)
        );
    }

    public WeatherDTO getWeatherOnHour(LocalDateTime dateTime) {
        return dtoService.constructFromHourly(
                weather.getHourly(dateTime)
        );
    }

    private String getFakeApi() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0));
        long unixTime = DateConverter.localDateTimeToUnixTime(localDateTime);
        return "{\"lat\":52.23,\"lon\":21.01,\"timezone\":\"Europe/Warsaw\",\"timezone_offset\":3600,\"hourly\":[{\"" +
                "dt\":" + (unixTime + 3600) + ",\"temp\":9.84,\"feels_like\":7.89,\"pressure\":1020,\"humidity\":87,\"dew_point\":7.78,\"clouds\":20,\"visibility\":10000,\"wind_speed\":2.05,\"wind_deg\":194,\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 7200) + ",\"temp\":9.64,\"feels_like\":7.32,\"pressure\":1020,\"humidity\":84,\"dew_point\":7.07,\"clouds\":59,\"visibility\":10000,\"wind_speed\":2.34,\"wind_deg\":192,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 10800) + ",\"temp\":10.18,\"feels_like\":7.76,\"pressure\":1020,\"humidity\":78,\"dew_point\":6.52,\"clouds\":83,\"visibility\":10000,\"wind_speed\":2.3,\"wind_deg\":191,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 14400) + ",\"temp\":10.81,\"feels_like\":8.3,\"pressure\":1020,\"humidity\":73,\"dew_point\":6.17,\"clouds\":93,\"visibility\":10000,\"wind_speed\":2.33,\"wind_deg\":189,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 18000) + ",\"temp\":10.92,\"feels_like\":8.34,\"pressure\":1020,\"humidity\":73,\"dew_point\":6.28,\"clouds\":98,\"visibility\":10000,\"wind_speed\":2.46,\"wind_deg\":181,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 21600) + ",\"temp\":10.78,\"feels_like\":8.35,\"pressure\":1019,\"humidity\":73,\"dew_point\":6.32,\"clouds\":97,\"visibility\":10000,\"wind_speed\":2.2,\"wind_deg\":179,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 25200) + ",\"temp\":10.62,\"feels_like\":8.19,\"pressure\":1018,\"humidity\":74,\"dew_point\":6.21,\"clouds\":92,\"visibility\":10000,\"wind_speed\":2.22,\"wind_deg\":171,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 28800) + ",\"temp\":10.04,\"feels_like\":7.25,\"pressure\":1018,\"humidity\":76,\"dew_point\":6.06,\"clouds\":93,\"visibility\":10000,\"wind_speed\":2.67,\"wind_deg\":166,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 32400) + ",\"temp\":9.52,\"feels_like\":6.51,\"pressure\":1018,\"humidity\":77,\"dew_point\":5.82,\"clouds\":81,\"visibility\":10000,\"wind_speed\":2.9,\"wind_deg\":168,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 36000) + ",\"temp\":9.3,\"feels_like\":6.05,\"pressure\":1018,\"humidity\":76,\"dew_point\":5.38,\"clouds\":78,\"visibility\":10000,\"wind_speed\":3.12,\"wind_deg\":173,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 39600) + ",\"temp\":9.17,\"feels_like\":5.82,\"pressure\":1018,\"humidity\":73,\"dew_point\":4.78,\"clouds\":81,\"visibility\":10000,\"wind_speed\":3.06,\"wind_deg\":177,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 43200) + ",\"temp\":8.94,\"feels_like\":5.66,\"pressure\":1018,\"humidity\":72,\"dew_point\":4.25,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.84,\"wind_deg\":182,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 46800) + ",\"temp\":8.56,\"feels_like\":5.39,\"pressure\":1018,\"humidity\":72,\"dew_point\":3.97,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.59,\"wind_deg\":173,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 50400) + ",\"temp\":8.39,\"feels_like\":5.05,\"pressure\":1018,\"humidity\":73,\"dew_point\":4.01,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.84,\"wind_deg\":162,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 54000) + ",\"temp\":8.27,\"feels_like\":4.88,\"pressure\":1018,\"humidity\":75,\"dew_point\":4.28,\"clouds\":100,\"visibility\":10000,\"wind_speed\":2.98,\"wind_deg\":159,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0},{\"" +
                "dt\":" + (unixTime + 57600) + ",\"temp\":8.04,\"feels_like\":4.66,\"pressure\":1018,\"humidity\":77,\"dew_point\":4.39,\"clouds\":100,\"visibility\":10000,\"wind_speed\":3.01,\"wind_deg\":157,\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"pop\":0}],\"daily\": [{\"" +
                "dt\":" + (unixTime) + ",\"sunrise\":1605419836,\"sunset\":1605451450,\"temp\":{\"day\":9.64,\"min\":7.63,\"max\":10.69,\"night\":8.39,\"eve\":10.03,\"morn\":7.63},\"feels_like\":{\"day\":7.32,\"night\":5.05,\"eve\":7.24,\"morn\":5.37},\"pressure\":1020,\"humidity\":84,\"dew_point\":7.07,\"wind_speed\":2.34,\"wind_deg\":192,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":59,\"pop\":0,\"uvi\":0.73},{\"" +
                "dt\":" + (unixTime + 86400) + ",\"sunrise\":1605506342,\"sunset\":1605537768,\"temp\":{\"day\":7.34,\"min\":5.9,\"max\":8.98,\"night\":8.74,\"eve\":8.68,\"morn\":6.92},\"feels_like\":{\"day\":3.04,\"night\":6.1,\"eve\":5.42,\"morn\":2.69},\"pressure\":1016,\"humidity\":76,\"dew_point\":3.53,\"wind_speed\":4.1,\"wind_deg\":154,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":71,\"pop\":0.89,\"rain\":0.89,\"uvi\":0.71},{\"" +
                "dt\":" + (unixTime + 172800) + ",\"sunrise\":1605592847,\"sunset\":1605624087,\"temp\":{\"day\":8.34,\"min\":6.45,\"max\":9.06,\"night\":8.73,\"eve\":8.98,\"morn\":6.45},\"feels_like\":{\"day\":5.03,\"night\":6.25,\"eve\":6.73,\"morn\":3.57},\"pressure\":1025,\"humidity\":79,\"dew_point\":4.98,\"wind_speed\":3.1,\"wind_deg\":242,\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":84,\"pop\":0.69,\"uvi\":0.71},{\"" +
                "dt\":" + (unixTime + 259200) + ",\"sunrise\":1605679352,\"sunset\":1605710409,\"temp\":{\"day\":8.12,\"min\":6.94,\"max\":9.22,\"night\":7.41,\"eve\":8.53,\"morn\":6.94},\"feels_like\":{\"day\":5.18,\"night\":3.17,\"eve\":5.43,\"morn\":4.31},\"pressure\":1031,\"humidity\":84,\"dew_point\":5.64,\"wind_speed\":2.76,\"wind_deg\":197,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":100,\"pop\":0.22,\"rain\":0.17,\"uvi\":0.76},{\"" +
                "dt\":" + (unixTime + 345600) + ",\"sunrise\":1605765855,\"sunset\":1605796734,\"temp\":{\"day\":6.69,\"min\":4.75,\"max\":9.71,\"night\":6.92,\"eve\":8.79,\"morn\":5.29},\"feels_like\":{\"day\":1.52,\"night\":2.21,\"eve\":4.17,\"morn\":0.7},\"pressure\":1022,\"humidity\":78,\"dew_point\":3.17,\"wind_speed\":5.27,\"wind_deg\":164,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":2,\"pop\":0,\"uvi\":0.8},{\"" +
                "dt\":" + (unixTime + 432000) + ",\"sunrise\":1605852358,\"sunset\":1605883061,\"temp\":{\"day\":7.47,\"min\":3.25,\"max\":7.95,\"night\":3.25,\"eve\":5.64,\"morn\":7.71},\"feels_like\":{\"day\":2.43,\"night\":-1.16,\"eve\":1.14,\"morn\":4.03},\"pressure\":1016,\"humidity\":74,\"dew_point\":3.17,\"wind_speed\":5.09,\"wind_deg\":268,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":100,\"pop\":1,\"rain\":1.39,\"uvi\":0.64},{\"" +
                "dt\":" + (unixTime + 518400) + ",\"sunrise\":1605938859,\"sunset\":1605969391,\"temp\":{\"day\":1.97,\"min\":0.39,\"max\":3.9,\"night\":1.05,\"eve\":2.72,\"morn\":1.17},\"feels_like\":{\"day\":-3.13,\"night\":-3.43,\"eve\":-2.2,\"morn\":-3.39},\"pressure\":1024,\"humidity\":82,\"dew_point\":-3.15,\"wind_speed\":4.29,\"wind_deg\":262,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"clouds\":0,\"pop\":0,\"uvi\":0.59},{\"" +
                "dt\":" + (unixTime + 604800) + ",\"sunrise\":1606025359,\"sunset\":1606055723,\"temp\":{\"day\":4.26,\"min\":1.06,\"max\":5.79,\"night\":5.79,\"eve\":5.4,\"morn\":1.37},\"feels_like\":{\"day\":-2.47,\"night\":-0.58,\"eve\":-0.21,\"morn\":-4.85},\"pressure\":1020,\"humidity\":63,\"dew_point\":-7.94,\"wind_speed\":6.36,\"wind_deg\":207,\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":100,\"pop\":0.58,\"rain\":0.51,\"uvi\":0.59}]}";
    }
}
