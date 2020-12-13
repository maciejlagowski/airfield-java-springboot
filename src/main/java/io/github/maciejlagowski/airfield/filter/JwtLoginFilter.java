package io.github.maciejlagowski.airfield.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.maciejlagowski.airfield.exception.UserNotActiveException;
import io.github.maciejlagowski.airfield.exception.UserNotFoundException;
import io.github.maciejlagowski.airfield.model.dto.JwtDTO;
import io.github.maciejlagowski.airfield.model.dto.UserDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import io.github.maciejlagowski.airfield.model.service.JwtService;
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
import java.util.LinkedList;

@AllArgsConstructor
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            User user = userRepository.findByEmail(userDTO.getEmail())
                    .orElseThrow(() -> new UserNotFoundException(userDTO.getEmail()));
            if (user.getRole().equals(ERole.ROLE_INACTIVE)) {
                throw new UserNotActiveException(userDTO.getEmail());
            }
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    new LinkedList<>()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtDTO jwtDTO = jwtService.buildJwt(authResult.getName(), 60);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.CREATED.value());
        response.getWriter().write(new ObjectMapper().writeValueAsString(jwtDTO));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
