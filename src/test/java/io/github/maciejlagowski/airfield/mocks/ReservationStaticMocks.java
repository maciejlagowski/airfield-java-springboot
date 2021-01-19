package io.github.maciejlagowski.airfield.mocks;

import io.github.maciejlagowski.airfield.controller.ReservationController;
import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.service.ReservationService;
import io.github.maciejlagowski.airfield.model.service.ReservationStatusService;
import org.mockito.AdditionalAnswers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReservationStaticMocks {

    public static ReservationController buildReservationController() {
        return new ReservationController(
                buildReservationService(),
                buildReservationStatusService(),
                mockJwtService(),
                buildEmailService(),
                buildUserService()
        );
    }

    public static ReservationService buildReservationService() {
        return new ReservationService(mockUserRepository(), mockReservationRepository());
    }

    public static ReservationStatusService buildReservationStatusService() {
        return new ReservationStatusService(mockReservationRepository());
    }

    public static ReservationRepository mockReservationRepository() {
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        when(reservationRepository.saveWithHoursCheck(any(Reservation.class))).then(AdditionalAnswers.returnsFirstArg());
        when(reservationRepository.findById(anyLong())).thenAnswer((invocation) -> Optional.of(buildReservation(invocation.getArgument(0))));
        when(reservationRepository.findAllByDateOrderByStartTime(any(LocalDate.class))).thenReturn(List.of(buildReservation(0L), buildReservation(1L)));
        return reservationRepository;
    }

    public static ReservationDTO buildReservationDTO(Long id) {
        return ReservationDTO.builder()
                .reservationId(id)
                .date(LocalDate.of(2021, 1, 1))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(10, 0))
                .reservationType(EReservationType.FLIGHT)
                .status(EStatus.NEW)
                .telephone("123456789")
                .userId(id)
                .name("Test Name")
                .build();
    }

    public static Reservation buildReservation(Long id) {
        ReservationDTO dto = buildReservationDTO(id);
        return Reservation.builder()
                .id(dto.getReservationId())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .reservationType(dto.getReservationType())
                .status(dto.getStatus())
                .user(buildUser(dto.getUserId()))
                .build();
    }

    public static Reservation buildReservationWithStatus(EStatus status) {
        Reservation reservation = buildReservation(0L);
        reservation.setStatus(status);
        return reservation;
    }

}
