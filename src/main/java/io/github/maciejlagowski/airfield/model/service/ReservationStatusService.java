package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import io.github.maciejlagowski.airfield.model.repository.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ReservationStatusService {

    private final ReservationRepository reservationRepository;

    @Scheduled(fixedDelay = 60000)  // One minute scheduling
    public void checkAndUpdateReservationsStatus() { // TODO only for one day
        reservationRepository.findAll().forEach(reservation -> {
            EStatus properStatus = checkWhatStatusShouldBe(reservation);
            if (!properStatus.equals(reservation.getStatus())) {
                updateStatus(reservation.getId(), properStatus);
            } // TODO tests
        });
    }

    public Long updateStatus(Long id, EStatus status) {
        reservationRepository.updateStatus(id, status);
        return id;
    }

    public EStatus checkWhatStatusShouldBe(Reservation reservation) {
        EStatus status = reservation.getStatus();
        switch (status) {
            case NEW:
                LocalDateTime startTime = LocalDateTime.of(reservation.getDate(), reservation.getStartTime().plusMinutes(15));
                if (LocalDateTime.now().isAfter(startTime)) {
                    return EStatus.REJECTED;
                }
                break;
            case ACCEPTED:
                LocalDateTime endTime = LocalDateTime.of(reservation.getDate(), reservation.getEndTime());
                if (LocalDateTime.now().isAfter(endTime)) {
                    return EStatus.DONE;
                }
        }
        return status;
    }
}
