package io.github.maciejlagowski.airfield.model.enumeration;

public enum EAlertImpact {
    UNKNOWN, YELLOW, AMBER, RED;

    public static EAlertImpact getImpactFromString(String event) {
        if (event.toLowerCase().contains("yellow")) {
            return YELLOW;
        } else if (event.toLowerCase().contains("amber")) {
            return AMBER;
        } else if (event.toLowerCase().contains("red")) {
            return RED;
        } else {
            return UNKNOWN;
        }
    }
}
