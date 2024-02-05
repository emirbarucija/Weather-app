package com.example.weatherapp.externalapi.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalApiTemperature {
    private Byte min;
    private Byte max;
}
