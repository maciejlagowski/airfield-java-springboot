package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailService {

    private final UserService userService;
    private final JavaMailSenderImpl mailSender;

    public void sendMail(String to, String text, String subject) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom("airfield.app@gmail.com");
        helper.setTo(to);
        helper.setText(text, true);
        helper.setSubject(subject);
        mailSender.send(message);

    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void sendActivationLink(UserDTO user) throws MessagingException {
        String message = "Hello " + user.getName() + "!<br /><br />" +
                "If you want to activate your account on Airfield Application, please click this link: " +
                "<a href=\"http://localhost:8080/users/activate?token=" + user.getToken() + "\">Activate</a><br /><br />" +
                "Have a nice day!<br />------------------------------------<br />Airfield Application";
        sendMail(user.getEmail(), message, "Registration in Airfield Application");
    }
}
