package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;

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
            reservation = new Reservation(
                    0,
                    reservationDTO.getDate(),
                    reservationDTO.getStartTime(),
                    reservationDTO.getEndTime(),
                    user,
                    reservationDTO.getStatus(),
                    reservationDTO.getReservationType()
                    );
        } catch (NoSuchElementException e) {
            //TODO ERROR
            e.printStackTrace();
            throw new NoSuchElementException("User " + reservationDTO.getUserId() + " doesn't exist");
        }
        return reservation;
    }
}
