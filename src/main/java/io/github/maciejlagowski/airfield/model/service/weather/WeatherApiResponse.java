
package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import javassist.NotFoundException;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;

@Data
class WeatherApiResponse {

    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lon")
    private Double longitude;
    private String timezone;
    @JsonProperty("timezone_offset")
    private Long timezoneOffset;
    @JsonProperty("hourly")
    private List<Hourly> hours;
    @JsonProperty("daily")
    private List<Daily> days;
    private List<Alert> alerts = new LinkedList<>();

    public Daily getDaily(LocalDateTime date) throws NotFoundException {
        for (Daily day : days) {
            if (date.toLocalDate().equals(day.getDateTime().toLocalDate())) {
                return day;
            }
        }
        throw new NotFoundException("Cannot get forecast on day " + date.toLocalDate());
    }

    public Hourly getHourly(LocalDateTime dateTime) {
        // TODO algorithm for best hour
        return hours.get(0);
    }

    public ZoneId getZone() {
        return ZoneId.of(timezone);
    }
}
