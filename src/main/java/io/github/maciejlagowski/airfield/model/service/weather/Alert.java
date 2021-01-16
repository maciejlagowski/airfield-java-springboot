package io.github.maciejlagowski.airfield.model.service.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.maciejlagowski.airfield.model.helper.DateHelper;
import lombok.Data;

import java.time.LocalDateTime;

@Data
class Alert {

    @JsonProperty("sender_name")
    private String senderName;
    private String event;
    @JsonProperty("start")
    private Long startTime;
    @JsonProperty("end")
    private Long endTime;
    private String description;

    public LocalDateTime getStartTime() {
        return DateHelper.unixTimeToLocalDateTime(startTime);
    }

    public LocalDateTime getEndTime() {
        return DateHelper.unixTimeToLocalDateTime(endTime);
    }
}
