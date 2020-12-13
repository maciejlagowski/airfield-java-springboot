package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum ERole {
    ROLE_USER, ROLE_EMPLOYEE, ROLE_ADMIN, ROLE_INACTIVE;

    public static ERole rand() { // TODO delete in final
        if (new Random().nextBoolean()) {
            return ROLE_USER;
        } else {
            return ROLE_EMPLOYEE;
        }
    }
}
