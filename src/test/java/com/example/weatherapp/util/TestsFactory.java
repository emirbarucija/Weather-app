package com.example.weatherapp.util;

import com.example.weatherapp.externalapi.model.ExternalApiResponse;
import com.example.weatherapp.externalapi.model.ExternalApiTemperature;
import com.example.weatherapp.externalapi.model.ExternalApiWeather;
import com.example.weatherapp.model.db.DailyWeather;
import com.example.weatherapp.model.db.SevenDaysWeather;
import com.example.weatherapp.model.dto.DailyWeatherDTO;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.List;

import static com.example.weatherapp.util.WindSpeedEnum.STRONG;

@UtilityClass
public class TestsFactory {
    private static final int SEVEN_DAYS_WEATHER_ID = 1;
    private static final Instant INSERT_TIME = Instant.now();

    private static final long DATE = 20240203L;
    private static final String DAY = "Saturday";
    private static final String WEATHER = "lightrain";
    private static final byte MIN_TEMPERATURE = 3;
    private static final byte MAX_TEMPERATURE = 4;
    private static final byte WIND_SPEED_ENUM = STRONG.getId();
    private static final String WIND_SPEED_RANGE = STRONG.getSpeedRange();
    private static final String WIND_SPEED_NAME = STRONG.getWindName();

    private static final String PRODUCT = "civillight";
    private static final String INIT = "2024020312";

    public static SevenDaysWeather createSevenDaysWeather() {
        return SevenDaysWeather.builder()
                .id(SEVEN_DAYS_WEATHER_ID)
                .insertTime(INSERT_TIME)
                .build();
    }

    public static DailyWeather createDailyWeather(SevenDaysWeather sevenDaysWeather) {
        return DailyWeather.builder()
                .date(DATE)
                .weatherType(WEATHER)
                .minTemperature(MIN_TEMPERATURE)
                .maxTemperature(MAX_TEMPERATURE)
                .windSpeedEnum(WIND_SPEED_ENUM)
                .sevenDaysWeather(sevenDaysWeather)
                .build();
    }

    public static ExternalApiResponse createExternalApiResponse() {
        return ExternalApiResponse.builder()
                .product(PRODUCT)
                .init(INIT)
                .externalApiWeatherList(List.of(createExternalApiWeather()))
                .build();
    }

    public static ExternalApiWeather createExternalApiWeather() {
        return ExternalApiWeather.builder()
                .date(DATE)
                .weather(WEATHER)
                .temperature(ExternalApiTemperature.builder()
                        .min(MIN_TEMPERATURE)
                        .max(MAX_TEMPERATURE)
                        .build())
                .windSpeedEnum(WIND_SPEED_ENUM)
                .build();
    }

    public static String createJsonString() {
        return "{ \"product\" : \"civillight\" , \"init\" : \"2024020312\" , " +
                "\"dataseries\" : [ { \"date\" : 20240203, \"weather\" : \"lightrain\", " +
                "\"temp2m\" : { \"max\" : 4, \"min\" : 3 }, \"wind10m_max\" : 5 } ] }";
    }

    public static ExternalApiResponse expectedExternalApiResponse() {
        return ExternalApiResponse.builder()
                .product(PRODUCT)
                .init(INIT)
                .externalApiWeatherList(List.of(createExternalApiWeather()))
                .build();
    }

    public static List<DailyWeatherDTO> expectedDailyWeatherDTOs() {
        return List.of(DailyWeatherDTO.builder()
                .day(DAY)
                .weatherType(WEATHER)
                .minTemperature(MIN_TEMPERATURE)
                .maxTemperature(MAX_TEMPERATURE)
                .windSpeedRange(WIND_SPEED_RANGE)
                .windName(WIND_SPEED_NAME)
                .build());
    }
}
