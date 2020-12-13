package io.github.maciejlagowski.airfield.model.service;

import io.github.maciejlagowski.airfield.model.dto.JwtDTO;
import io.github.maciejlagowski.airfield.model.entity.User;
import io.github.maciejlagowski.airfield.model.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey signKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final UserRepository userRepository;

    public Long getUserIdFromJwt(String jwt) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(signKey).build()
                .parseClaimsJws(jwt.replace("Bearer ", ""));
        String id = claimsJws.getBody().get("sub").toString();
        return Long.parseLong(id);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationByToken(String header) {
        Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(signKey).build()
                .parseClaimsJws(header.replace("Bearer ", ""));

        String userId = claimsJws.getBody().get("sub").toString();
        String role = claimsJws.getBody().get("role").toString();
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority(role));

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }

    public JwtDTO buildJwt(String email, long expirationDelayMinutes) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user " + email + " in database"));
        long currTime = System.currentTimeMillis();
        long expirationTime = currTime + expirationDelayMinutes * 60000;
        return new JwtDTO(Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim("role", user.getRole())
                .setIssuedAt(new Date(currTime))
                .setExpiration(new Date(expirationTime))
                .signWith(signKey)
                .compact(),
                expirationTime,
                user.getRole());
    }
}
