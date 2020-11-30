package io.github.maciejlagowski.airfield.model.entity;

import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime startTime;
    @Column(nullable = false)
    private LocalTime endTime;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Enumerated(EnumType.STRING)
    private EReservationType reservationType;
    // TODO NotNull not nullable

//    public Reservation(long id, LocalDate date, LocalTime startTime, LocalTime endTime, User user, ReservationType reservationType, Status status) {
//        if (endTime.isBefore(startTime)) {
//            throw new IllegalArgumentException("End time cannot be earlier than start time");
//        }
//        this.id = id;
//        this.date = date;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.user = user;
//        this.reservationType = reservationType;
//        this.status = status;
//    }

    public boolean collides(Reservation reservation) {
        return (this.startTime.isAfter(reservation.startTime) && this.startTime.isBefore(reservation.endTime)) ||
                (this.endTime.isAfter(reservation.startTime) && this.endTime.isBefore(reservation.endTime)) ||
                (this.startTime.isBefore(reservation.startTime) && this.endTime.isAfter(reservation.endTime)) ||
                (reservation.startTime.isBefore(this.startTime) && reservation.endTime.isAfter(this.endTime));

    }
}
