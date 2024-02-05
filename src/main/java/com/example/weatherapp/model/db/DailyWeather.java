package com.example.weatherapp.model.db;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Long date;

    @Column(length = 30)
    private String weatherType;

    private Byte minTemperature;

    private Byte maxTemperature;

    private Byte windSpeedEnum;

    @ManyToOne
    @JoinColumn(name = "seven_days_weather_id", nullable = false)
    private SevenDaysWeather sevenDaysWeather;
}
