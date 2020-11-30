package io.github.maciejlagowski.airfield.model.entity;

import io.github.maciejlagowski.airfield.model.enumeration.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String name;
    private String passwordHash;
    @Column(nullable = false)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Role> roles;

    public Set<ERole> getRoles() {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
