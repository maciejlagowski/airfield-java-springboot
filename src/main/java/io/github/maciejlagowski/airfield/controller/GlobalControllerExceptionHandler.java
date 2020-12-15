package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.AirfieldApplication;
import io.github.maciejlagowski.airfield.exception.ObjectAlreadyInDatabaseException;
import io.github.maciejlagowski.airfield.exception.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SignatureException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException(HttpServletResponse httpServletResponse, NotFoundException e) throws IOException {
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

    @ExceptionHandler({IllegalAccessException.class, ObjectAlreadyInDatabaseException.class})
    public void handleIllegalAccessException(HttpServletResponse httpServletResponse, Exception e) throws IOException {
        if (AirfieldApplication.debug)
            e.printStackTrace();
        httpServletResponse.sendError(400, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public void handleOtherExceptions(HttpServletResponse httpServletResponse, Exception e) throws IOException {
        if (AirfieldApplication.debug)
            e.printStackTrace();
        httpServletResponse.sendError(500, e.getMessage());

    }
}
