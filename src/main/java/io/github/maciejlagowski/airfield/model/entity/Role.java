package io.github.maciejlagowski.airfield.model.entity;

import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(String name) {
        this.name = ERole.valueOf(name);
    }

    public Role(ERole name) {
        this.name = name;
    }
}
