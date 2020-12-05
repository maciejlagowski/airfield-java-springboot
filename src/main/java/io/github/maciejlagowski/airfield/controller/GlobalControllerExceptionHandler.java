package io.github.maciejlagowski.airfield.controller;

import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse httpServletResponse, NotFoundException exception) throws IOException {
        httpServletResponse.sendError(404, exception.getMessage());
    }
}
