package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.mocks.UserStaticMocks;
import io.github.maciejlagowski.airfield.model.dto.JwtDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.buildUser;
import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService(UserStaticMocks.mockUserRepository());

    @Test
    void shouldGetUserIdFromJwt() {
        User user = buildUser(0L);
        JwtDTO jwtDTO = jwtService.buildJwt(user.getEmail(), 60);
        Long id = jwtService.getUserIdFromJwt(jwtDTO.getToken());

        assertEquals(user.getId(), id);
    }

    @Test
    void shouldGetAuthenticationByToken() {
        User user = buildUser(0L);
        JwtDTO jwtDTO = jwtService.buildJwt(user.getEmail(), 60);
        UsernamePasswordAuthenticationToken token = jwtService.getAuthenticationByToken(jwtDTO.getToken());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        assertTrue(token.isAuthenticated());
        assertTrue(token.getAuthorities().contains(authority));
    }

    @Test
    void shouldBuildJwt() {
        User user = buildUser(0L);
        JwtDTO jwtDTO = jwtService.buildJwt(user.getEmail(), 60);
        double expTime = 60.0 * 60000 + System.currentTimeMillis();

        assertEquals(user.getName(), jwtDTO.getName());
        assertNotNull(jwtDTO.getToken());
        assertEquals(user.getRole(), jwtDTO.getRole());
        assertEquals(expTime, (double) jwtDTO.getExpirationTime(), 1000); //one second delta
    }
}
