package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum EStatus {
    NEW, ACCEPTED, CANCELLED, REJECTED;

    public static EStatus rand() { // TODO delete in final
        EStatus[] statuses = EStatus.values();
        return statuses[new Random().nextInt(statuses.length)];
    }
}
