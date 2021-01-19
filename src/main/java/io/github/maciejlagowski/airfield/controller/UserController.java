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

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

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

    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'EMPLOYEE', 'ADMIN')")
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO user, HttpServletRequest request) throws IllegalAccessException {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (!userService.getRole(request).equals(ERole.ROLE_ADMIN) && !userId.equals(id)) {
            throw new IllegalAccessException("User is trying to update another user");
        }
        return userService.update(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Long deleteUser(@PathVariable Long id, HttpServletRequest request) throws IllegalAccessException {
        Long userId = jwtService.getUserIdFromJwt(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (userService.isRegularUser(userId)) {
            if (!userId.equals(id)) {
                throw new IllegalAccessException("User cannot delete another user");
            }
        }
        return userService.deleteById(id);
    }

    @PatchMapping("/users/{id}/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUserRole(@PathVariable Long id, @RequestBody UserDTO user) {
        user.setId(id);
        return userService.updateRole(user);
    }

    @GetMapping("/users/activate")
    @ResponseStatus(HttpStatus.OK)
    public String activate(@RequestParam String token) {
        userService.activateUser(token);
        return "User activated! You can close this page.";
    }

    @GetMapping("/users/logged")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN', 'EMPLOYEE')")
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
