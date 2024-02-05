package com.example.weatherapp.service;

import com.example.weatherapp.model.db.SevenDaysWeather;
import com.example.weatherapp.repository.DailyWeatherRepository;
import com.example.weatherapp.repository.SevenDaysWeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.weatherapp.util.TestsFactory.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private SevenDaysWeatherRepository sevenDaysWeatherRepository;
    @Mock
    private DailyWeatherRepository dailyWeatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void given_sevenDaysWeather_when_saveDataToDB_then_dataSuccessfullySavedToDB() {
        // given
        SevenDaysWeather sevenDaysWeather = createSevenDaysWeather();
        when(sevenDaysWeatherRepository.save(any())).thenReturn(sevenDaysWeather);

        // when
        boolean savedSuccessfully = weatherService.saveDataToDB(createExternalApiResponse());

        // then
        assertTrue(savedSuccessfully);
        verify(dailyWeatherRepository, times(1)).saveAll(List.of(createDailyWeather(sevenDaysWeather)));
    }

    @Test
    public void given_sevenDaysWeatherRepositoryThrowsException_when_saveDataToDB_then_dataNotSavedToDB_and_dailyWeatherRepositoryNotInvoked() {
        // given
        when(sevenDaysWeatherRepository.save(any())).thenThrow(new RuntimeException("Something went wrong"));

        // when
        boolean savedSuccessfully = weatherService.saveDataToDB(createExternalApiResponse());

        // then
        assertFalse(savedSuccessfully);
        verify(dailyWeatherRepository, never()).saveAll(any());
    }

    @Test
    public void given_dailyWeatherRepositoryThrowsException_when_saveDataToDB_then_dataNotSavedToDB_and_sevenDaysWeatherDataCleanedUp() {
        // given
        when(sevenDaysWeatherRepository.save(any())).thenReturn(createSevenDaysWeather());
        when(dailyWeatherRepository.saveAll(any())).thenThrow(new RuntimeException("Something went wrong"));

        // when
        boolean savedSuccessfully = weatherService.saveDataToDB(createExternalApiResponse());

        // then
        assertFalse(savedSuccessfully);
        verify(sevenDaysWeatherRepository, times(1)).deleteById(eq(1));
    }
}
