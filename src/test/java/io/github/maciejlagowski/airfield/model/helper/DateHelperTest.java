package io.github.maciejlagowski.airfield.model.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateHelperTest {

    @Test
    void shouldConvertUnixTimeToLocalDateTime() {
        Long unixTime = 1610808247L;

        LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 16, 15, 44, 7);

        Assertions.assertEquals(localDateTime, DateHelper.unixTimeToLocalDateTime(unixTime));
        Assertions.assertNotEquals(LocalDateTime.now(), DateHelper.unixTimeToLocalDateTime(unixTime));
        Assertions.assertNotEquals(localDateTime, DateHelper.unixTimeToLocalDateTime(-1L));
    }

    @Test
    void shouldConvertLocalDateTimeToUnixTime() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 16, 14, 44, 7);

        Long unixTime = 1610808247L;

        Assertions.assertEquals(unixTime, DateHelper.localDateTimeToUnixTime(localDateTime));
        Assertions.assertNotEquals(unixTime, DateHelper.localDateTimeToUnixTime(LocalDateTime.now()));
        Assertions.assertNotEquals(unixTime, DateHelper.localDateTimeToUnixTime(LocalDateTime.MAX));
    }
}
