package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum ReservationType {
    FLIGHT, CONCERT, MOTORSPORTS, OTHER;

    public static ReservationType rand() { // TODO delete in final
        ReservationType[] types = ReservationType.values();
        return types[new Random().nextInt(types.length)];
    }
}
