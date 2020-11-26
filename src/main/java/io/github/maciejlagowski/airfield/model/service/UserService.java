package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.Role;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.RoleRepository;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserDTO user) {
        return userRepository.save(constructEntityFromDTO(user));
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user '" + name + "' in database"));
    }

    public User constructEntityFromDTO(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .passwordHash(passwordEncoder.encode(userDTO.getPassword()))
                .phoneNumber(userDTO.getPhoneNumber())
                .roles(userDTO.getRoles())
                .build();
    }
}
