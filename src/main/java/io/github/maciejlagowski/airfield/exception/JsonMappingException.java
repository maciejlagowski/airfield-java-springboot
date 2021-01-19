package io.github.maciejlagowski.airfield.exception;

public class JsonMappingException extends RuntimeException {
    public JsonMappingException(String message) {
        super("Cannot map JSON to Java Object. " + message);
    }
}
