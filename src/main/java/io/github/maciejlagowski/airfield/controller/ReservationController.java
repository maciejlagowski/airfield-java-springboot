package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.service.*;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
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
    private final EmailService emailService;
    private final UserService userService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'EMPLOYEE')")
    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationDTO> getReservations(@RequestParam String date, HttpServletRequest request) throws IllegalAccessException {
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
    void addReservation(@RequestBody ReservationDTO reservationDTO, HttpServletRequest request) {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (requestWrapper.isUserInRole(ERole.ROLE_USER.name())) {
            Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
            reservationDTO.setUserId(userId);
        }
        reservationService.saveWithHoursCheck(reservationDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'USER')")
    @PatchMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    void changeReservationStatus(@RequestParam Long id, @RequestParam EStatus status, HttpServletRequest request) throws MessagingException, IllegalAccessException {
        UserDTO userToChange = userService.getUserById(reservationService.getReservationById(id).getUserId());
        if (userService.isRegularUser(request)) {
            Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
            if (!userId.equals(userToChange.getId())) {
                throw new IllegalAccessException("User cannot modify another user reservation");
            }
        }
        reservationStatusService.updateStatus(id, status);
        emailService.sendReservationStatusChangedNotification(status, userToChange);
    }
}
