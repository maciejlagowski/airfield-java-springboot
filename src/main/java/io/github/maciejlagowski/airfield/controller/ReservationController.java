package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.service.JwtService;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import io.github.maciejlagowski.airfield.model.service.ReservationStatusService;
import lombok.Data;
import org.springframework.http.HttpHeaders;
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
    private final ReservationStatusService reservationStatusService;
    private final JwtService jwtService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservations(@RequestParam String date, HttpServletRequest request) {
        Long loggedUserId = this.jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        List<ReservationDTO> reservations = reservationService.findAllByDateOrdered(LocalDate.parse(date));
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (requestWrapper.isUserInRole(ERole.ROLE_USER.name())) {
            return reservationService.blackoutList(reservations, loggedUserId);
        }
        return reservations;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'EMPLOYEE')")
    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    void addReservation(@RequestBody ReservationDTO reservationDTO, HttpServletRequest request) throws IllegalAccessException {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (requestWrapper.isUserInRole(ERole.ROLE_USER.name())) {
            Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
            if (!userId.equals(reservationDTO.getUserId())) {
                throw new IllegalAccessException("User is trying to make reservation for another user");
            }
        }
        reservationService.saveWithHoursCheck(reservationDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PatchMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void changeReservationStatus(@RequestParam Long id, @RequestParam EStatus status) {
        reservationStatusService.updateStatus(id, status);
    }
}
