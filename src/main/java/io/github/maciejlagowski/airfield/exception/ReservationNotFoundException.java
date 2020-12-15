package io.github.maciejlagowski.airfield.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("Cannot find reservation in database.");
    }
}
