package com.example.weatherapp.repository;

import com.example.weatherapp.model.db.DailyWeather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface DailyWeatherRepository extends CrudRepository<DailyWeather, Integer> {

    @Query(value = "SELECT * FROM daily_weather dw " +
            "WHERE dw.seven_days_weather_id = (SELECT max(sdw.id) FROM seven_days_weather sdw)",
            nativeQuery = true)
    List<DailyWeather> findDailyWeatherForNextSevenDays();
}
