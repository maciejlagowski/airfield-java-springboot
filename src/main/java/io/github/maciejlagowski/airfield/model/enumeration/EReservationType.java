package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum EReservationType {
    FLIGHT, CONCERT, MOTORSPORTS, OTHER;

    public static EReservationType rand() { // TODO delete in final
        EReservationType[] types = EReservationType.values();
        return types[new Random().nextInt(types.length)];
    }
}
