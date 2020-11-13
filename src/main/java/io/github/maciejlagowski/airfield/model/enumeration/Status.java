package io.github.maciejlagowski.airfield.model.enumeration;

import java.util.Random;

public enum Status {
    NEW, ACCEPTED, CANCELLED, REJECTED, DONE;

    public static Status rand() { // TODO delete in final
        Status[] statuses = Status.values();
        return statuses[new Random().nextInt(statuses.length)];
    }
}
