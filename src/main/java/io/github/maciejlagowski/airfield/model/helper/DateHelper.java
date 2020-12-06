package io.github.maciejlagowski.airfield.model.helper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateHelper {

    public static LocalDateTime unixTimeToLocalDateTime(Long date) {
        return Instant.ofEpochMilli(date * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Long localDateTimeToUnixTime(LocalDateTime dateTime) {
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
