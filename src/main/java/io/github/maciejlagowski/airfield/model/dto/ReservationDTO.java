package io.github.maciejlagowski.airfield.model.dto;

import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private EReservationType reservationType;
    private Long userId;
    private Long reservationId;
    private EStatus status;
}
