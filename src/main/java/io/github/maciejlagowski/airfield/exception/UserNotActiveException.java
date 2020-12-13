package io.github.maciejlagowski.airfield.exception;

public class UserNotActiveException extends RuntimeException {
    public UserNotActiveException(String username) {
        super("User " + username + " is inactive. Please activate account by email.");
    }
}
