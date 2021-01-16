package io.github.maciejlagowski.airfield.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

class ReservationTests {

    @Test
    void shouldCheckReservationCollisions() {
        Reservation reservation = Reservation.builder()
                .startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(12, 0)).build();
        Reservation outerRes = Reservation.builder()
                .startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(13, 0)).build();
        Reservation innerRes = Reservation.builder()
                .startTime(LocalTime.of(10, 30)).endTime(LocalTime.of(11, 0)).build();
        Reservation leftRes = Reservation.builder()
                .startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(11, 0)).build();
        Reservation rightRes = Reservation.builder()
                .startTime(LocalTime.of(11, 0)).endTime(LocalTime.of(13, 0)).build();
        Reservation sameRes = Reservation.builder()
                .startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(12, 0)).build();
        Reservation goodRes = Reservation.builder()
                .startTime(LocalTime.of(15, 0)).endTime(LocalTime.of(18, 0)).build();

        Assertions.assertTrue(reservation.collides(outerRes));
        Assertions.assertTrue(reservation.collides(innerRes));
        Assertions.assertTrue(reservation.collides(leftRes));
        Assertions.assertTrue(reservation.collides(rightRes));
        Assertions.assertTrue(reservation.collides(sameRes));
        Assertions.assertFalse(reservation.collides(goodRes));

        Assertions.assertTrue(outerRes.collides(reservation));
        Assertions.assertTrue(innerRes.collides(reservation));
        Assertions.assertTrue(leftRes.collides(reservation));
        Assertions.assertTrue(rightRes.collides(reservation));
        Assertions.assertTrue(sameRes.collides(reservation));
        Assertions.assertFalse(goodRes.collides(reservation));
    }
}
