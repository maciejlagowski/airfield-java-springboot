package io.github.maciejlagowski.airfield.exception;

public class ObjectAlreadyInDatabaseException extends RuntimeException {
    public ObjectAlreadyInDatabaseException(String className) {
        super(className + " already exists in database");
    }
}
