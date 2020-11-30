package io.github.maciejlagowski.airfield.configuration;

import io.github.maciejlagowski.airfield.AirfieldApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtFilter extends BasicAuthenticationFilter {

    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authResult = getAuthenticationByToken(header);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {
        if (header.contains("login")) {
            return new UsernamePasswordAuthenticationToken("", "", null);
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(AirfieldApplication.keyForHS)
                .parseClaimsJws(header.replace("Bearer ", ""));

        String userId = claimsJws.getBody().get("sub").toString();
        ArrayList<String> roles = (ArrayList<String>) claimsJws.getBody().get("roles");
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(userId, null, simpleGrantedAuthorities);
    }
}
