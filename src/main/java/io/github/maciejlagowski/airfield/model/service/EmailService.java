package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.EmailDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.enumeration.EStatus;
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

    private final JavaMailSenderImpl mailSender;

    public void sendMail(EmailDTO email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setFrom("airfield.app@gmail.com");
        helper.setTo(email.getTo());
        helper.setText(email.getText(), true);
        helper.setSubject(email.getSubject());
        mailSender.send(message);
    }

    public void sendFormattedMail(EmailDTO email) throws MessagingException {
        String messageText = "Hello " + email.getUsername() + "!<br /><br />" +
                email.getText() + "<br /><br />Have a nice day!<br />" +
                "------------------------------------<br />" +
                "<a href=\"http://localhost:4200\">Airfield Application</a>";
        email.setText(messageText);
        sendMail(email);
    }

    public void sendReservationStatusChangedNotification(EStatus status, UserDTO user) throws MessagingException {
        EmailDTO email = buildEmailWithUserDTO(user);
        email.setText("Your reservation status has been changed to " + status + ".<br />" +
                "Please visit our application to see more details!");
        email.setSubject("Your reservation status changed in Airfield Application");
        sendFormattedMail(email);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public void sendActivationLink(UserDTO user) throws MessagingException {
        EmailDTO email = buildEmailWithUserDTO(user);
        email.setText("If you want to activate your account on Airfield Application, please click this link: "
                + "<a href=\"http://localhost:8080/users/activate?token=" + user.getToken() + "\">Activate</a>");
        email.setSubject("Registration in Airfield Application");
        sendFormattedMail(email);
    }

    public EmailDTO buildEmailWithUserDTO(UserDTO user) {
        return EmailDTO.builder()
                .username(user.getName())
                .to(user.getEmail())
                .build();
    }

    public void sendResetLink(UserDTO user) throws MessagingException {
        EmailDTO email = buildEmailWithUserDTO(user);
        email.setText("If you want to reset your password on Airfield Application, please click this link:"
                + "<a href=\"http://localhost:8080/users/reset-password?token=" + user.getToken() + "\">Reset password</a>");
        email.setSubject("Reset password in Airfield Application");
        sendFormattedMail(email);
    }
}
