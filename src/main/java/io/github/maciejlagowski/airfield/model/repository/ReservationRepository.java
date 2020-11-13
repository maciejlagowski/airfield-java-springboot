package io.github.maciejlagowski.airfield.model.repository;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findAllByDateOrderByStartTime(LocalDate date);

    default void saveWithHoursCheck(Reservation reservation, ReservationRepository repository) {
        List<Reservation> reservations = repository.findAllByDateOrderByStartTime(reservation.getDate());
        for (Reservation reservationFromDB: reservations) {
            if (reservation.collides(reservationFromDB)) {
                throw new IllegalArgumentException("Reservation time collides with already reserved elements");
            }
        }
        repository.save(reservation);
    }
}
