package io.github.maciejlagowski.airfield.model.enumeration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EAlertImpactTest {

    @Test
    void shouldGetImpactFromString() {
        Assertions.assertEquals(EAlertImpact.AMBER, EAlertImpact.getImpactFromString("sdfsdf AmBer:dsf"));
        Assertions.assertEquals(EAlertImpact.RED, EAlertImpact.getImpactFromString("sdfsdfRED:dsf"));
        Assertions.assertEquals(EAlertImpact.YELLOW, EAlertImpact.getImpactFromString("fgdyEllowsdf"));
        Assertions.assertEquals(EAlertImpact.UNKNOWN, EAlertImpact.getImpactFromString("sdfsdf :dsf"));
    }
}
