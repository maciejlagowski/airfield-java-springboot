package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.EmailDTO;
import io.github.maciejlagowski.airfield.model.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public EmailDTO sendEmail(@RequestBody EmailDTO mail) throws MessagingException {
        return emailService.sendFormattedMail(mail);
    }
}
