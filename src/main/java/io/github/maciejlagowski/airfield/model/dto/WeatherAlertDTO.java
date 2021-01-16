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
public class WeatherAlertDTO {
    private String senderName;
    private String event;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
}
