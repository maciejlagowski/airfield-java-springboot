package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.Status;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    @GetMapping("/reservations")
    public List<ReservationDTO> getReservations(@RequestParam String date) {
        List<Reservation> reservations = reservationRepository.findAllByDateOrderByStartTime(LocalDate.parse(date));
        return reservationService.reservationListToDTOList(reservations);
    }

    @PostMapping("/reservations")
    void addReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.createReservation(reservationDTO);
        reservationRepository.saveWithHoursCheck(reservation, reservationRepository);
    }

    @PatchMapping("/reservations")
    void changeReservationStatus(@RequestParam Long id, @RequestParam Status status) {
        reservationRepository.updateStatus(id, status);
    }
}
