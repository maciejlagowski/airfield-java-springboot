package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.exception.UserNotFoundException;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDTO user) {
        userRepository.save(constructEntityFromDTO(user));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    public User constructEntityFromDTO(UserDTO userDTO) {
        return User.builder()
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .passwordHash(passwordEncoder.encode(userDTO.getPassword()))
                .phoneNumber(userDTO.getPhoneNumber())
                .role(userDTO.getRole())
                .token(userDTO.getToken())
                .build();
    }

    public UserDTO constructDTOFromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream().map(this::constructDTOFromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        return constructDTOFromEntity(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException()));
    }

    public void update(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(UserNotFoundException::new);
        if (Objects.nonNull(userDTO.getName()))
            user.setName(userDTO.getName());
        if (Objects.nonNull(userDTO.getPhoneNumber()))
            user.setPhoneNumber(userDTO.getPhoneNumber());
        if (Objects.nonNull(userDTO.getPassword()))
            user.setPasswordHash(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);

    }

    public void activateUser(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(ERole.ROLE_USER);
        user.setToken(null);
        userRepository.save(user);
    }
}
