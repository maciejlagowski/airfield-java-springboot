package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class ReservationController {

    private final ReservationService reservationService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservationsBlacked(@RequestParam String date, HttpServletRequest request) {
        List<ReservationDTO> reservations = reservationService.findAllByDateOrdered(LocalDate.parse(date));
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (requestWrapper.isUserInRole(ERole.ROLE_USER.name())) {
            return reservationService.blackoutList(reservations);
        }
        return reservations;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'EMPLOYEE')")
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
