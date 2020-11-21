package io.github.maciejlagowski.airfield.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Object not found")
    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException() {
    }
}
