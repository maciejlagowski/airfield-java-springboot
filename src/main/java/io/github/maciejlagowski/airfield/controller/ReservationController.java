package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservations(@RequestParam String date) {
        List<Reservation> reservations = reservationService.findAllByDateOrdered(LocalDate.parse(date));
        return reservationService.reservationListToDTOList(reservations);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void addReservation(@RequestBody ReservationDTO reservationDTO) {
        reservationService.saveWithHoursCheck(reservationDTO);
    }

    @PatchMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void changeReservationStatus(@RequestParam Long id, @RequestParam EStatus status) {
        reservationService.updateStatus(id, status);
    }
}
