package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Data
public class UserController {

    private final UserRepository userRepository;

//    @GetMapping("/login")
//    public boolean login(@RequestParam User user) {
//        System.out.println("got login" + user);
//        return user.getName().equals("root")    // TODO login
//                && user.getPassword().equals("maciek");
//    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    void addUser(@RequestBody User user) {
        userRepository.save(user);
    }
}
