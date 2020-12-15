package io.github.maciejlagowski.airfield.model.entity;

import io.github.maciejlagowski.airfield.model.enumeration.EReservationType;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    @Enumerated(EnumType.STRING)
    private EReservationType reservationType;

    public boolean collides(Reservation reservation) {
        return (this.startTime.isAfter(reservation.startTime) && this.startTime.isBefore(reservation.endTime)) ||
                (this.endTime.isAfter(reservation.startTime) && this.endTime.isBefore(reservation.endTime)) ||
                (this.startTime.isBefore(reservation.startTime) && this.endTime.isAfter(reservation.endTime)) ||
                (reservation.startTime.isBefore(this.startTime) && reservation.endTime.isAfter(this.endTime));

    }
}
