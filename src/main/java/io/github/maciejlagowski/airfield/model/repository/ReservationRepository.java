package io.github.maciejlagowski.airfield.model.repository;

import io.github.maciejlagowski.airfield.model.entity.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {
}
