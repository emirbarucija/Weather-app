package com.example.weatherapp.controller;

import com.example.weatherapp.model.db.DailyWeather;
import com.example.weatherapp.model.dto.DailyWeatherDTO;
import com.example.weatherapp.repository.DailyWeatherRepository;
import com.example.weatherapp.util.DailyWeatherFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/weather")
@Slf4j
@AllArgsConstructor
public class WeatherController {
    private static final String EXCEPTION_MESSAGE = "Exception happened while getting weather for next seven days";

    private final DailyWeatherRepository dailyWeatherRepository;
    private final ThreadPoolTaskExecutor weatherControllerThreadPoolTaskExecutor;

    @GetMapping(path = "/nextSevenDays")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<DailyWeatherDTO> getWeatherForNextSevenDays() {
        log.info("Received request for getting weather for next seven days");

        try {
            Future<List<DailyWeatherDTO>> future = weatherControllerThreadPoolTaskExecutor.submit(this::getDailyWeatherDTOS);
            List<DailyWeatherDTO> dailyWeatherDTOS = future.get();

            log.info("Returning response: {}", dailyWeatherDTOS);
            return dailyWeatherDTOS;
        } catch (TaskRejectedException e) {
            log.error("Weather Controller Thread pool is full, task is rejected", e);
            throw new RuntimeException(EXCEPTION_MESSAGE);
        } catch (Exception e) {
            log.error(EXCEPTION_MESSAGE, e);
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
    }

    private List<DailyWeatherDTO> getDailyWeatherDTOS() {
        List<DailyWeather> dailyWeathers = dailyWeatherRepository.findDailyWeatherForNextSevenDays();

        return dailyWeathers.stream()
                .map(DailyWeatherFactory::convertToDailyWeatherDTO)
                .collect(Collectors.toList());
    }
}
