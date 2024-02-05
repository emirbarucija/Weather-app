package com.example.weatherapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum WindSpeedEnum {
    CALM((byte) 1, "Below 0.3m/s", "calm"),
    LIGHT((byte) 2, "0.3-3.4m/s", "light"),
    MODERATE((byte) 3, "3.4-8.0m/s", "moderate"),
    FRESH((byte) 4, "8.0-10.8m/s", "fresh"),
    STRONG((byte) 5, "10.8-17.2m/s", "strong"),
    GALE((byte) 6, "17.2-24.5m/s", "gale"),
    STORM((byte) 7, "24.5-32.6m/s", "storm"),
    HURRICANE((byte) 8, "Over 32.6m/s", "hurricane");

    private final Byte id;
    private final String speedRange;
    private final String windName;

    public static Optional<WindSpeedEnum> getFromId(byte id) {
        return Stream.of(values())
                .filter(windSpeedEnum -> id == windSpeedEnum.id)
                .findFirst();
    }
}
