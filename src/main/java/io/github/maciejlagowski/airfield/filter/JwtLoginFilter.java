package io.github.maciejlagowski.airfield.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maciejlagowski.airfield.AirfieldApplication;
import io.github.maciejlagowski.airfield.model.dto.JwtDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@AllArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO userDTO = new ObjectMapper()
                    .readValue(request.getInputStream(), UserDTO.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    Collections.EMPTY_LIST));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = userService.findByEmail(authResult.getName()).get();

        long currTime = System.currentTimeMillis();
        long expirationTime = currTime + 60000 * 60; // 60 minutes expiration
//        long expirationTime = currTime + 60000 * 15; // 15 minutes expiration
//        long expirationTime = currTime + 6000; // 6 seconds expiration
        JwtDTO jwtDTO = new JwtDTO(Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("role", user.getRole())
                .setIssuedAt(new Date(currTime))
                .setExpiration(new Date(expirationTime))
                .signWith(AirfieldApplication.keyForHS)
                .compact(),
                expirationTime,
                user.getRole());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.CREATED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtDTO));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
