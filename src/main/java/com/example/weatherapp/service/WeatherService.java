package com.example.weatherapp.service;

import com.example.weatherapp.externalapi.model.ExternalApiResponse;
import com.example.weatherapp.model.db.DailyWeather;
import com.example.weatherapp.model.db.SevenDaysWeather;
import com.example.weatherapp.repository.DailyWeatherRepository;
import com.example.weatherapp.repository.SevenDaysWeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.weatherapp.util.DailyWeatherFactory.createDailyWeather;

@Service
@Slf4j
@AllArgsConstructor
public class WeatherService {

    private final SevenDaysWeatherRepository sevenDaysWeatherRepository;
    private final DailyWeatherRepository dailyWeatherRepository;

    public boolean saveDataToDB(ExternalApiResponse externalApiResponse) {
        SevenDaysWeather sevenDaysWeather = SevenDaysWeather.builder()
                .insertTime(Instant.now())
                .build();
        log.debug("Saving SevenDaysWeather to DB: {}", sevenDaysWeather);
        SevenDaysWeather sevenDaysWeatherResult;
        try {
            sevenDaysWeatherResult = sevenDaysWeatherRepository.save(sevenDaysWeather);
            log.debug("SevenDaysWeather saved to DB: {}", sevenDaysWeatherResult);
        } catch (Exception e) {
            log.error("Exception happened while saving SevenDaysWeather to DB", e);
            return false;
        }

        List<DailyWeather> dailyWeathers = externalApiResponse.getExternalApiWeatherList().stream()
                .map(externalApiWeather -> createDailyWeather(externalApiWeather, sevenDaysWeatherResult))
                .collect(Collectors.toList());

        log.debug("Saving DailyWeather entries to DB: {}", dailyWeathers);
        try {
            Iterable<DailyWeather> dailyWeathersResult = dailyWeatherRepository.saveAll(dailyWeathers);
            log.debug("DailyWeather entries saved to DB: {}", dailyWeathersResult);
            return true;
        } catch (Exception e) {
            log.error("Exception happened while saving DailyWeathers to DB, deleting SevenDaysWeather record", e);
            sevenDaysWeatherRepository.deleteById(sevenDaysWeatherResult.getId());
            return false;
        }
    }
}
