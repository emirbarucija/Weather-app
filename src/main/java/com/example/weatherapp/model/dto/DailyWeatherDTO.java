package com.example.weatherapp.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DailyWeatherDTO {

    private String day;

    private String weatherType;

    private Byte minTemperature;

    private Byte maxTemperature;

    private String windSpeedRange;

    private String windName;
}
