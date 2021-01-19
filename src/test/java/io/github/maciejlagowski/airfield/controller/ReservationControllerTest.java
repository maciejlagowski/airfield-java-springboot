package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

import static io.github.maciejlagowski.airfield.mocks.ReservationStaticMocks.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ReservationControllerTest {

    private final ReservationController reservationController = buildReservationController();

    @Test
    void shouldGetReservations() throws IllegalAccessException {
        List<ReservationDTO> reservations =
                reservationController.getReservations(LocalDate.now().toString(), mock(HttpServletRequest.class));

        assertNotNull(reservations);
    }

    @Test
    void shouldAddReservation() {
        Reservation reservation =
                reservationController.addReservation(buildReservationDTO(0L), mock(HttpServletRequest.class));

        assertEquals(buildReservation(0L), reservation);
    }

    @Test
    void shouldChangeReservationStatus() throws MessagingException, IllegalAccessException {
        Long id = reservationController.changeReservationStatus(0L, EStatus.DONE, mock(HttpServletRequest.class));

        assertEquals(0L, id);
    }

    @Test
    void shouldThrowIllegalAccessOnChangeReservationStatus() {
        assertThrows(IllegalAccessException.class,
                () -> reservationController.changeReservationStatus(5L, EStatus.DONE, mock(HttpServletRequest.class)));
    }
}
