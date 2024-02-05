package com.example.weatherapp.externalapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalApiResponse {
    private String product;
    private String init;
    @JsonProperty("dataseries")
    private List<ExternalApiWeather> externalApiWeatherList;
}
