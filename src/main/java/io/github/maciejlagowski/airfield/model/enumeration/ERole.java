package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum ERole {
    ROLE_USER, ROLE_EMPLOYEE, ROLE_ADMIN;

    public static ERole rand() { // TODO delete in final
        ERole[] roles = ERole.values();
        return roles[new Random().nextInt(roles.length)];
    }
}
