package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.service.EmailService;
import io.github.maciejlagowski.airfield.model.service.JwtService;
import io.github.maciejlagowski.airfield.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody UserDTO user) throws MessagingException {
        String token = emailService.generateToken();
        user.setToken(token);
        user.setRole(ERole.ROLE_INACTIVE);
        emailService.sendActivationLink(user);
        userService.save(user);
    }

    // TODO activation on frontend?
    @GetMapping("/users/activate")
    @ResponseStatus(HttpStatus.OK)
    public String activate(@RequestParam String token) {
        userService.activateUser(token);
        return "User activated! You can close this page.";
    }

    @GetMapping("/users/logged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'EMPLOYEE')")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getLoggedUser(HttpServletRequest request) {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userService.getUserById(userId);
    }

    @PutMapping("/users")
    @PreAuthorize("hasAnyRole('USER', 'EMPLOYEE', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody UserDTO user, HttpServletRequest request) throws IllegalAccessException {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (userId.equals(user.getId())) {
            userService.update(user);
        } else {
            throw new IllegalAccessException("User is trying to update another user");
        }
    }

}
