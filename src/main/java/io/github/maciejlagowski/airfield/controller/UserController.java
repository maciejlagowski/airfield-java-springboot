package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.exception.UserNotActiveException;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.service.EmailService;
import io.github.maciejlagowski.airfield.model.service.JwtService;
import io.github.maciejlagowski.airfield.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @PostMapping("/users/register")
    @ResponseStatus(HttpStatus.OK)
    public User register(@RequestBody UserDTO user) throws MessagingException {
        String token = emailService.generateToken();
        user.setToken(token);
        user.setRole(ERole.ROLE_INACTIVE);
        User savedUser = userService.save(user);
        emailService.sendActivationLink(user);
        return savedUser;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/users")
    @PreAuthorize("hasAnyRole('USER', 'EMPLOYEE', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@RequestBody UserDTO user, HttpServletRequest request) throws IllegalAccessException {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (!userService.getRole(request).equals(ERole.ROLE_ADMIN) && !userId.equals(user.getId())) {
            throw new IllegalAccessException("User is trying to update another user");
        }
        return userService.update(user);
    }

    @PatchMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public User updateUserRole(@RequestBody UserDTO user) {
        return userService.updateRole(user);
    }

    @DeleteMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteUser(@RequestParam Long id, HttpServletRequest request) throws IllegalAccessException {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (userService.isRegularUser(userId)) {
            if (!userId.equals(id)) {
                throw new IllegalAccessException("User cannot delete another user");
            }
        }
        return userService.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/users/all")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

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

    @PatchMapping("/users/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public User sendResetPasswordLink(@RequestParam String email) throws MessagingException {
        String token = emailService.generateToken();
        UserDTO user = userService.getUserByEmail(email);
        if (user.getRole().equals(ERole.ROLE_INACTIVE)) {
            throw new UserNotActiveException(user.getEmail());
        }
        user.setToken(token);
        User updatedUser = userService.update(user);
        emailService.sendResetLink(user);
        return updatedUser;
    }

    @GetMapping("/users/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public String resetPassword(@RequestParam String token) {
        String tempPassword = userService.resetPassword(token);
        return "Your new temporary password is '" + tempPassword + "'. Please change it with first login.";
    }
}
