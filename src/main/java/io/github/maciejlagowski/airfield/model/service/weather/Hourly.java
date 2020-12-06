
package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maciejlagowski.airfield.model.helper.DateHelper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
class Hourly {

    @JsonProperty("dt")
    private Long dateTime;
    private Double temp;
    @JsonProperty("feels_like")
    private Double feelsLike;
    private Long pressure;
    private Long humidity;
    @JsonProperty("dew_point")
    private Double dewPoint;
    @JsonIgnore
    private Double uvi;
    private Long clouds;
    private Long visibility;
    @JsonProperty("wind_speed")
    private Double windSpeed;
    @JsonProperty("wind_gust")
    @JsonIgnore
    private Double windGust;
    @JsonProperty("wind_deg")
    private Long windDeg;
    private List<Weather> weather;
    private Double pop;
    @JsonIgnore
    private Object rain;
    @JsonIgnore
    private Object snow;

    public LocalDateTime getDateTime() {
        return DateHelper.unixTimeToLocalDateTime(dateTime);
    }

    public Weather getWeather() {
        return weather.get(0);
    }
}
