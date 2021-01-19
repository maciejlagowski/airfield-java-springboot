package io.github.maciejlagowski.airfield.controller;

import io.github.maciejlagowski.airfield.exception.UserNotActiveException;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.service.UserService;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class UserControllerTest {

    private final UserController userController = buildUserController();
    private final UserService userService = buildUserService();

    @Test
    void shouldRegisterUser() throws MessagingException {
        UserDTO userDTO = buildUserDTO(0L);

        User user = userController.register(userDTO);

        assertEquals(userDTO.getEmail(), user.getEmail());
        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getPhoneNumber(), user.getPhoneNumber());
        assertNotNull(user.getToken());
    }

    @Test
    void shouldGetUser() {
        UserDTO user = userController.getUser(1L);

        assertNotNull(user);
    }

    @Test
    void shouldUpdateUser() throws IllegalAccessException {
        UserDTO userDTO = buildUserDTO(0L);

        User user = userController.updateUser(0L, userDTO, mock(HttpServletRequest.class));

        assertEquals(userDTO.getName(), user.getName());
        assertEquals(userDTO.getPhoneNumber(), user.getPhoneNumber());
    }

    @Test
    void shouldThrowIllegalAccessOnUpdateUser() {
        assertThrows(IllegalAccessException.class,
                () -> userController.updateUser(5L, buildUserDTO(5L), mock(HttpServletRequest.class)));
    }

    @Test
    void shouldUpdateUserRole() {
        UserDTO userDTO = buildUserDTO(0L);

        User user = userController.updateUserRole(0L, userDTO);

        assertEquals(userDTO.getRole(), user.getRole());
    }

    @Test
    void shouldDeleteUser() throws IllegalAccessException {
        Long id = userController.deleteUser(0L, mock(HttpServletRequest.class));

        assertEquals(0L, id);
    }

    @Test
    void shouldThrowIllegalAccessOnDeleteUser() {
        assertThrows(IllegalAccessException.class,
                () -> userController.deleteUser(5L, mock(HttpServletRequest.class)));
    }

    @Test
    void shouldGetAllUsers() {
        List<UserDTO> users = userController.getAllUsers();

        assertNotNull(users);
    }

    @Test
    void shouldActivateUser() {
        String message = userController.activate("testToken");

        assertEquals("User activated! You can close this page.", message);
    }

    @Test
    void shouldGetLoggedUser() {
        UserDTO user = userController.getLoggedUser(mock(HttpServletRequest.class));
        user.setPassword("password");

        assertEquals(buildUserDTO(0L), user);
    }

    @Test
    void shouldSendResetPasswordLink() throws MessagingException {
        User user = userController.sendResetPasswordLink("abc@mail.com");

        assertEquals("abc@mail.com", user.getEmail());
        assertNotNull(user.getToken());
    }

    @Test
    void shouldThrowUserNotActiveOnSendResetLink() {
        assertThrows(UserNotActiveException.class,
                () -> userController.sendResetPasswordLink("inactive@mail.com"));
    }

    @Test
    void shouldResetPassword() {
        String message = userController.resetPassword("TestToken");
        assertNotNull(message);
    }

    @Test
    void shouldConvertUsersBothWay() {
        User user = buildUser(0L);
        UserDTO userDTO = userService.constructDTOFromEntity(user);
        User userAfterConversion = userService.constructEntityFromDTO(userDTO);
        userAfterConversion.setPasswordHash("passwordHash");

        UserDTO userDTO1 = buildUserDTO(0L);
        User user1 = userService.constructEntityFromDTO(userDTO1);
        UserDTO userDTOAfterConversion = userService.constructDTOFromEntity(user1);
        userDTOAfterConversion.setPassword("password");

        assertEquals(user, userAfterConversion);
        assertEquals(userDTO1, userDTOAfterConversion);
    }
}
