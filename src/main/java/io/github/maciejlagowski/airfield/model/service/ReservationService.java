package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.ReservationDTO;
import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    @Autowired
    private UserRepository userRepository;

    public ReservationDTO constructFromEntity(Reservation reservation) {
        return ReservationDTO.builder()
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .name(reservation.getUser().getName())
                .telephone(reservation.getUser().getEmail())    //TODO TELEPHONE
                .reservationType(reservation.getReservationType())
                .userId(reservation.getUser().getId())
                .reservationId(reservation.getId())
                .build();
    }

    public List<ReservationDTO> reservationListToDTOList(List<Reservation> reservations) {
        List<ReservationDTO> dtoList = new LinkedList<>();
        reservations.forEach(reservation -> {
            dtoList.add(constructFromEntity(reservation));
        });
        return dtoList;
    }

    public Reservation createReservation(ReservationDTO reservationDTO) throws NoSuchElementException {
        Reservation reservation;
        try {
            User user = userRepository.findById(reservationDTO.getUserId()).orElseThrow();
            reservation = new Reservation(
                    0,
                    reservationDTO.getStartDate(),
                    reservationDTO.getEndDate(),
                    user,
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
