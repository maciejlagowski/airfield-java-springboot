package io.github.maciejlagowski.airfield.mocks;

import io.github.maciejlagowski.airfield.controller.UserController;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import io.github.maciejlagowski.airfield.model.service.EmailService;
import io.github.maciejlagowski.airfield.model.service.JwtService;
import io.github.maciejlagowski.airfield.model.service.UserService;
import org.mockito.AdditionalAnswers;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserStaticMocks {


    public static UserController buildUserController() {
        return new UserController(
                buildUserService(),
                mockJwtService(),
                buildEmailService()
        );
    }

    public static UserService buildUserService() {
        return new UserService(mockUserRepository(), mock(PasswordEncoder.class));
    }

    public static UserRepository mockUserRepository() {
        UserRepository repo = mock(UserRepository.class);
        when(repo.save(any(User.class))).then(AdditionalAnswers.returnsFirstArg());
        when(repo.findById(anyLong())).thenAnswer((invocation) -> Optional.of(buildUser(invocation.getArgument(0))));
        when(repo.findByToken(anyString())).thenReturn(Optional.of(buildUser(0L)));
        when(repo.findByEmail("inactive@mail.com")).thenReturn(Optional.of(buildInactiveUser(0L)));
        when(repo.findByEmail("abc@mail.com")).thenReturn(Optional.of(buildUser(0L)));
        return repo;
    }

    public static UserDTO buildUserDTO(Long id) {
        return UserDTO.builder()
                .id(id)
                .email("abc@mail.com")
                .name("Test Name")
                .phoneNumber("123456789")
                .role(ERole.ROLE_USER)
                .password("password")
                .build();
    }


    public static User buildUser(Long id) {
        UserDTO userDTO = buildUserDTO(id);
        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .passwordHash("passwordHash")
                .build();
    }

    private static User buildInactiveUser(Long id) {
        User user = buildUser(id);
        user.setRole(ERole.ROLE_INACTIVE);
        return user;
    }

    public static JwtService mockJwtService() {
        JwtService jwtService = mock(JwtService.class);
        when(jwtService.getUserIdFromJwt(anyString())).thenReturn(0L);
        return jwtService;
    }

    public static EmailService buildEmailService() {
        JavaMailSenderImpl mailSender = mock(JavaMailSenderImpl.class);
        when(mailSender.createMimeMessage()).thenReturn(mock(MimeMessage.class));
        return new EmailService(mailSender);
    }
}
