package io.github.maciejlagowski.airfield.filter;

import io.github.maciejlagowski.airfield.model.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends BasicAuthenticationFilter {

    private final JwtService jwtService;

    public JwtFilter(AuthenticationManager authenticationManager, JwtService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && !header.equals("")) {
            try {
                UsernamePasswordAuthenticationToken authResult = jwtService.getAuthenticationByToken(header);
                SecurityContextHolder.getContext().setAuthentication(authResult);
                chain.doFilter(request, response);
            } catch (Exception e) {
                response.sendError(401, "Authorization error, please try to log in or re-login.");
            }
        }
    }
}
