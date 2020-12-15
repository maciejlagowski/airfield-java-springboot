package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.EmailDTO;
import io.github.maciejlagowski.airfield.model.service.EmailService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class EmailController {

    private final EmailService emailService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailDTO mail) throws MessagingException {
        emailService.sendFormattedMail(mail);
    }
}
