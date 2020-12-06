package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maciejlagowski.airfield.model.helper.DateHelper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@ToString
@Setter
class Daily extends Hourly {

    private Long sunrise;
    private Long sunset;
    @JsonProperty("temp")
    private Temp temperature;
    @JsonProperty("feels_like")
    private Temp feelsLikeTemp;
    @JsonIgnore
    private Double uvi;

    public LocalDateTime getSunrise() {
        return DateHelper.unixTimeToLocalDateTime(sunrise);
    }

    public LocalDateTime getSunset() {
        return DateHelper.unixTimeToLocalDateTime(sunset);
    }
}
