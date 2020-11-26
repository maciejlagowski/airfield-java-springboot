package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservations(@RequestParam String date) {
        List<Reservation> reservations = reservationRepository.findAllByDateOrderByStartTime(LocalDate.parse(date));
        return reservationService.reservationListToDTOList(reservations);
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void addReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.createReservation(reservationDTO);
        reservationRepository.saveWithHoursCheck(reservation, reservationRepository);
    }

    @PatchMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void changeReservationStatus(@RequestParam Long id, @RequestParam EStatus status) {
        reservationRepository.updateStatus(id, status);
    }
}
