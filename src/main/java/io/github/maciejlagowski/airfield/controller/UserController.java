package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.AirfieldApplication;
import io.github.maciejlagowski.airfield.model.dto.JwtDTO;
import io.github.maciejlagowski.airfield.model.dto.LoginDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public JwtDTO login(@RequestBody LoginDTO user) {
        long currTime = System.currentTimeMillis();
        System.out.println("uuuuuuuuser " + user.getName());
        Set<ERole> roles = userService.loginUser(user);
        if (roles.contains(ERole.ROLE_NOT_LOGGED)) {
            return new JwtDTO(ERole.ROLE_NOT_LOGGED.name());
        }
        long id = userService.findUserByName(user.getName()).getId();
        return new JwtDTO(Jwts.builder()
                .setSubject(Long.toString(id))
                .claim("roles", roles)
                .setIssuedAt(new Date(currTime))
                .setExpiration(new Date(currTime + 60000 * 15)) //15 minutes expiration
                .signWith(AirfieldApplication.keyForHS)
                .compact());
    }

//    @PostMapping("/register")
//    public void register(@RequestBody User user) {
//        System.out.println("got reg req " + user);
//
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.findAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    void addUser(@RequestBody UserDTO user) {
        userService.save(user);
    }
}
