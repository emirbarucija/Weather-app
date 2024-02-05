package com.example.weatherapp.controller;

import com.example.weatherapp.model.dto.DailyWeatherDTO;
import com.example.weatherapp.repository.DailyWeatherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;

import static com.example.weatherapp.util.TestsFactory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private DailyWeatherRepository dailyWeatherRepository;
    private ThreadPoolTaskExecutor weatherControllerThreadPoolTaskExecutor;

    private WeatherController weatherController;

    @BeforeEach
    public void init() {
        ThreadPoolTaskExecutor weatherControllerThreadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        weatherControllerThreadPoolTaskExecutor.setCorePoolSize(1);
        weatherControllerThreadPoolTaskExecutor.setMaxPoolSize(1);
        weatherControllerThreadPoolTaskExecutor.setQueueCapacity(1);
        weatherControllerThreadPoolTaskExecutor.initialize();

        weatherController = new WeatherController(dailyWeatherRepository, weatherControllerThreadPoolTaskExecutor);
    }

    @Test
    public void given_lastSevenDaysData_when_getWeatherForNextSevenDays_then_properResultReturned() {
        // given
        when(dailyWeatherRepository.findDailyWeatherForNextSevenDays()).thenReturn(List.of(createDailyWeather(createSevenDaysWeather())));

        // when
        List<DailyWeatherDTO> dailyWeatherDTOS = weatherController.getWeatherForNextSevenDays();

        // then
        assertEquals(expectedDailyWeatherDTOs(), dailyWeatherDTOS);
    }
}
