package com.example.weatherapp.util;

import com.example.weatherapp.externalapi.model.ExternalApiWeather;
import com.example.weatherapp.model.db.DailyWeather;
import com.example.weatherapp.model.db.SevenDaysWeather;
import com.example.weatherapp.model.dto.DailyWeatherDTO;
import lombok.experimental.UtilityClass;

import java.util.Optional;

import static com.example.weatherapp.util.DateUtil.getDay;
import static com.example.weatherapp.util.WindSpeedEnum.getFromId;

@UtilityClass
public class DailyWeatherFactory {

    public static DailyWeather createDailyWeather(ExternalApiWeather externalApiWeather,
                                                  SevenDaysWeather sevenDaysWeather) {
        return DailyWeather.builder()
                .sevenDaysWeather(sevenDaysWeather)
                .date(externalApiWeather.getDate())
                .weatherType(externalApiWeather.getWeather())
                .minTemperature(externalApiWeather.getTemperature().getMin())
                .maxTemperature(externalApiWeather.getTemperature().getMax())
                .windSpeedEnum(externalApiWeather.getWindSpeedEnum())
                .build();
    }

    public static DailyWeatherDTO convertToDailyWeatherDTO(DailyWeather dailyWeather) {
        DailyWeatherDTO dailyWeatherDTO = DailyWeatherDTO.builder()
                .day(getDay(dailyWeather.getDate()))
                .weatherType(dailyWeather.getWeatherType())
                .minTemperature(dailyWeather.getMinTemperature())
                .maxTemperature(dailyWeather.getMaxTemperature())
                .build();

        Optional<WindSpeedEnum> windSpeedEnumOptional = getFromId(dailyWeather.getWindSpeedEnum());
        if (windSpeedEnumOptional.isPresent()) {
            WindSpeedEnum windSpeedEnum = windSpeedEnumOptional.get();
            dailyWeatherDTO.setWindSpeedRange(windSpeedEnum.getSpeedRange());
            dailyWeatherDTO.setWindName(windSpeedEnum.getWindName());

            return dailyWeatherDTO;
        } else {
            throw new IllegalArgumentException("WindSpeedEnum with Id " + dailyWeather.getWindSpeedEnum() + " does not exist");
        }
    }
}
