package io.github.maciejlagowski.airfield.model.dto;

import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtDTO {
    private String token;
    private Long expirationTime;
    private ERole role;
    private String name;
}
