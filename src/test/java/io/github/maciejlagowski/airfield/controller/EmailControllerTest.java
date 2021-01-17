package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.EmailDTO;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.buildEmailService;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailControllerTest {

    @Test
    void sendEmail() throws MessagingException {
        EmailController emailController = new EmailController(buildEmailService());
        EmailDTO email = EmailDTO.builder()
                .to("abc@mail.com")
                .subject("Test subject")
                .text("Test text")
                .username("Test username")
                .build();
        email = emailController.sendEmail(email);

        assertEquals("Hello Test username!<br /><br />" +
                "Test text<br /><br />Have a nice day!<br />" +
                "------------------------------------<br />" +
                "<a href=\"http://localhost:4200\">Airfield Application</a>", email.getText());
    }
}
