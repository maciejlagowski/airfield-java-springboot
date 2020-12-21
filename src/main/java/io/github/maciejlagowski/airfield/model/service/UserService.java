package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.exception.ObjectAlreadyInDatabaseException;
import io.github.maciejlagowski.airfield.exception.UserNotFoundException;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserDTO user) {
        try {
            userRepository.save(constructEntityFromDTO(user));
        } catch (Exception e) {
            throw new ObjectAlreadyInDatabaseException("User");
        }
    }

    public UserDTO getUserByEmail(String email) {
        return constructDTOFromEntity(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(email)));
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
                .orElseThrow(UserNotFoundException::new));
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
        if (Objects.nonNull(userDTO.getToken()))
            user.setToken(userDTO.getToken());
        userRepository.save(user);
    }

    public void activateUser(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(UserNotFoundException::new);
        user.setRole(ERole.ROLE_USER);
        user.setToken(null);
        userRepository.save(user);
    }

    public String resetPassword(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(UserNotFoundException::new);
        String tempPassword = generateTempPassword();
        user.setPasswordHash(passwordEncoder.encode(tempPassword));
        user.setToken(null);
        userRepository.save(user);
        return tempPassword;
    }

    public String generateTempPassword() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public boolean isRegularUser(HttpServletRequest request) {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        return requestWrapper.isUserInRole(ERole.ROLE_USER.name());
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public ERole getRole(HttpServletRequest request) {
        SecurityContextHolderAwareRequestWrapper requestWrapper = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        for (ERole role : ERole.values()) {
            if (requestWrapper.isUserInRole(role.name())) {
                return role;
            }
        }
        return ERole.ROLE_NOT_LOGGED;
    }

    public void updateRole(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(UserNotFoundException::new);
        if (Objects.nonNull(userDTO.getRole()))
            user.setRole(userDTO.getRole());
        userRepository.save(user);
    }
}
