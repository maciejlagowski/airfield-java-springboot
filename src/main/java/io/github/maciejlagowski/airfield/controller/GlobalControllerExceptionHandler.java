package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.AirfieldApplication;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse httpServletResponse, NotFoundException e) throws IOException {
        if (AirfieldApplication.debug)
            e.printStackTrace();
        httpServletResponse.sendError(404, e.getMessage());
    }

    @ExceptionHandler(SignatureException.class)
    public void handleSignatureException(HttpServletResponse httpServletResponse, SignatureException e) throws IOException {
        if (AirfieldApplication.debug)
            e.printStackTrace();
        httpServletResponse.sendError(401, "User login problem, please try to logout and login again");
    }
}
