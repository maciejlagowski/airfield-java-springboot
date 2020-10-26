package io.github.maciejlagowski.airfield.model.dto;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReservationDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String name;
    private String telephone;
    private ReservationType reservationType;
    private Long userId;
    private Long reservationId;
}
