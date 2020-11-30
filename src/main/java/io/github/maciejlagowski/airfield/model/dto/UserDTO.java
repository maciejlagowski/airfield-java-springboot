package io.github.maciejlagowski.airfield.model.dto;

import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private String name;
    private String password;
    private String phoneNumber;
    private Set<ERole> roles;
}
