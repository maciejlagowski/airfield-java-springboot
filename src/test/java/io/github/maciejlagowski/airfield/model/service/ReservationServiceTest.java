package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.maciejlagowski.airfield.mocks.ReservationStaticMocks.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ReservationServiceTest {

    private final ReservationService reservationService = buildReservationService();

    @Test
    void shouldCheckReservationStatus() {
        ReservationStatusService service = new ReservationStatusService(Mockito.mock(ReservationRepository.class));
        LocalDateTime endTime = LocalDateTime.now().minusHours(3);
        LocalDateTime startTime = endTime.minusHours(1);

        Reservation acceptedRes = Reservation.builder()
                .startTime(startTime.toLocalTime()).endTime(endTime.toLocalTime()).date(startTime.toLocalDate())
                .status(EStatus.ACCEPTED).build();
        Reservation newRes = Reservation.builder()
                .startTime(startTime.toLocalTime()).endTime(endTime.toLocalTime()).date(startTime.toLocalDate())
                .status(EStatus.NEW).build();
        Reservation doneRes = Reservation.builder()
                .startTime(startTime.toLocalTime()).endTime(endTime.toLocalTime()).date(startTime.toLocalDate())
                .status(EStatus.DONE).build();

        assertEquals(EStatus.DONE,
                service.checkWhatStatusShouldBe(acceptedRes));
        assertEquals(EStatus.REJECTED, service.checkWhatStatusShouldBe(newRes));
        assertEquals(EStatus.DONE, service.checkWhatStatusShouldBe(doneRes));
    }

    @Test
    void shouldConvertReservationsBothWay() {
        Reservation reservation = buildReservation(0L);
        ReservationDTO reservationDTO = reservationService.constructFromEntity(reservation);
        Reservation reservationAfterConversion = reservationService.createReservation(reservationDTO);

        ReservationDTO reservationDTO1 = buildReservationDTO(0L);
        Reservation reservation1 = reservationService.createReservation(reservationDTO1);
        ReservationDTO reservationDTO1AfterConversion = reservationService.constructFromEntity(reservation1);

        assertEquals(reservation, reservationAfterConversion);
        assertEquals(reservationDTO1, reservationDTO1AfterConversion);
    }

    @Test
    void shouldConvertListOfReservations() {
        List<Reservation> reservations = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            reservations.add(buildReservation(0L));
        }

        List<ReservationDTO> reservationDTOs = reservationService.reservationListToDTOList(reservations);

        List<Reservation> reservationsAfterConversion = reservationDTOs.stream()
                .map(reservationService::createReservation).collect(Collectors.toList());

        assertEquals(reservations, reservationsAfterConversion);
    }

    @Test
    void shouldBlackoutConfidentialData() {
        ReservationDTO reservation = buildReservationDTO(0L);
        reservation = reservationService.blackoutConfidentialData(reservation);

        assertEquals("********", reservation.getName());
        assertEquals("********", reservation.getTelephone());
    }

    @Test
    void shouldBlackoutListOfReservations() {
        List<ReservationDTO> reservations = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            reservations.add(buildReservationDTO((long) i));
        }

        reservations = reservationService.blackoutList(reservations, 4L);

        ReservationDTO notBlackedReservation = reservations.remove(4);

        reservations.forEach(reservation -> {
            assertEquals("********", reservation.getName());
            assertEquals("********", reservation.getTelephone());
        });

        assertEquals("Test Name", notBlackedReservation.getName());
        assertEquals("123456789", notBlackedReservation.getTelephone());
    }
}
