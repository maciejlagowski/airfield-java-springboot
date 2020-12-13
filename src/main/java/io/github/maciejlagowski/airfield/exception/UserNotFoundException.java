package io.github.maciejlagowski.airfield.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("Cannot find user " + username + " in database.");
    }

    public UserNotFoundException() {
        super("Cannot find user in database.");
    }
}
