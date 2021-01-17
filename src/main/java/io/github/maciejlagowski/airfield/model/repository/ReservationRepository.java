package io.github.maciejlagowski.airfield.model.repository;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findAllByDateOrderByStartTime(LocalDate date);

    default void saveWithHoursCheck(Reservation reservation) {
        List<Reservation> reservations = findAllByDateOrderByStartTime(reservation.getDate());
//        for (Reservation reservationFromDB : reservations) {
//            if (reservation.collides(reservationFromDB)) {
//                throw new IllegalArgumentException("Reservation time collides with already reserved elements");
//            }
//        }
        save(reservation);
    }

    @Modifying
    @Query("update Reservation r set r.status = :status where r.id = :id")
    void updateStatus(@Param(value = "id") long id, @Param(value = "status") EStatus status);

}
