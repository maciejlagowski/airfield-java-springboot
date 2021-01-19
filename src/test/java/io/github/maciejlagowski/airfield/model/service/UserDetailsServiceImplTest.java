package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.buildUser;
import static io.github.maciejlagowski.airfield.mocks.UserStaticMocks.mockUserRepository;

class UserDetailsServiceImplTest {

    @Test
    void shouldLoadUserByUsername() {
        User user = buildUser(0L);
        UserDetails userDetails = new UserDetailsServiceImpl(mockUserRepository())
                .loadUserByUsername(user.getEmail());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());

        Assertions.assertTrue(userDetails.getAuthorities().contains(authority));
        Assertions.assertEquals(user.getEmail(), userDetails.getUsername());
    }
}
