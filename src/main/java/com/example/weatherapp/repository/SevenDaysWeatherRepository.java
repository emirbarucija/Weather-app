package com.example.weatherapp.repository;

import com.example.weatherapp.model.db.SevenDaysWeather;
import org.springframework.data.repository.CrudRepository;

public interface SevenDaysWeatherRepository extends CrudRepository<SevenDaysWeather, Integer> {

}
