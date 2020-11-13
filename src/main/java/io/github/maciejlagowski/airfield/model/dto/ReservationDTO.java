package io.github.maciejlagowski.airfield.model.dto;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.ReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReservationDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String name;
    private String telephone;
    private ReservationType reservationType;
    private Long userId;
    private Long reservationId;
    private Status status;
}
