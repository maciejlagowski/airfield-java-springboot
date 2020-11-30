package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.LoginDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.Role;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDTO user) {
        userRepository.save(constructEntityFromDTO(user));
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
                .roles(userDTO.getRoles().stream().map(Role::new).collect(Collectors.toSet()))
                .build();
    }

    public UserDTO constructDTOFromEntity(User user) {
        return UserDTO.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .build();
    }

    public Set<ERole> loginUser(LoginDTO loginDTO) {
        if (userRepository.findByName(loginDTO.getName()).isEmpty()) {
            return Set.of(ERole.ROLE_NOT_LOGGED);
        }
        User user = userRepository.findByName(loginDTO.getName()).get();
        if (passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            return user.getRoles();
        } else {
            return Set.of(ERole.ROLE_NOT_LOGGED);
        }
    }

    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map(this::constructDTOFromEntity).collect(Collectors.toList());
    }
}
