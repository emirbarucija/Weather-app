package com.example.weatherapp.model.db;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SevenDaysWeather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Instant insertTime;

    @OneToMany(mappedBy = "sevenDaysWeather")
    private List<DailyWeather> dailyWeathers;
}
