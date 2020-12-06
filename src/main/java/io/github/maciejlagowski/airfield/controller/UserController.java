package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/register")
//    public void register(@RequestBody User user) {
//        System.out.println("got reg req " + user);
//
//    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE')")
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    void addUser(@RequestBody UserDTO user) {
        userService.save(user);
    }
}
