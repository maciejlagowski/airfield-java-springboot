package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public ReservationDTO constructFromEntity(Reservation reservation) {
        return ReservationDTO.builder()
                .date(reservation.getDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .name(reservation.getUser().getName())
                .telephone(reservation.getUser().getPhoneNumber())
                .reservationType(reservation.getReservationType())
                .userId(reservation.getUser().getId())
                .reservationId(reservation.getId())
                .status(reservation.getStatus())
                .build();
    }

    public List<ReservationDTO> reservationListToDTOList(List<Reservation> reservations) {
        List<ReservationDTO> dtoList = new LinkedList<>();
        reservations.forEach(reservation -> dtoList.add(constructFromEntity(reservation)));
        return dtoList;
    }

    public Reservation createReservation(ReservationDTO reservationDTO) throws NoSuchElementException {
        Reservation reservation;
        System.err.println(reservationDTO);
        try {
            User user = userRepository.findById(reservationDTO.getUserId()).orElseThrow();
            reservation = Reservation.builder()
                    .date(reservationDTO.getDate())
                    .startTime(reservationDTO.getStartTime())
                    .endTime(reservationDTO.getEndTime())
                    .user(user)
                    .status(reservationDTO.getStatus())
                    .reservationType(reservationDTO.getReservationType())
                    .build();
        } catch (NoSuchElementException e) {
            //TODO ERROR
            e.printStackTrace();
            throw new NoSuchElementException("User " + reservationDTO.getUserId() + " doesn't exist");
        }
        return reservation;
    }

    public List<ReservationDTO> findAllByDateOrdered(LocalDate date) {
        return reservationListToDTOList(
                reservationRepository.findAllByDateOrderByStartTime(date)
        );
    }

    public void updateStatus(Long id, EStatus status) {
        reservationRepository.updateStatus(id, status);
    }

    public void saveWithHoursCheck(ReservationDTO reservationDTO) {
        Reservation reservation = createReservation(reservationDTO);
        reservationRepository.saveWithHoursCheck(reservation);
    }

    public ReservationDTO blackoutConfidentialData(ReservationDTO reservation) {
        reservation.setName("********");
        reservation.setTelephone("********");
        return reservation;
    }

    public List<ReservationDTO> blackoutList(List<ReservationDTO> reservations) {
        List<ReservationDTO> reservationDTOs = new LinkedList<>();
        reservations.forEach(reservation -> {
            EStatus status = reservation.getStatus();
            if (status.equals(EStatus.ACCEPTED) || status.equals(EStatus.NEW)) {
                reservationDTOs.add(
                        blackoutConfidentialData(reservation)
                );
            }
        });
        return reservationDTOs;
    }
}
