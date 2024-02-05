package com.example.weatherapp.externalapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalApiWeather {
    private Long date;
    private String weather;
    @JsonProperty("temp2m")
    private ExternalApiTemperature temperature;
    @JsonProperty("wind10m_max")
    private Byte windSpeedEnum;
}
