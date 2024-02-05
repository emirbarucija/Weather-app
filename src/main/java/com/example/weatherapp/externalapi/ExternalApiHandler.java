package com.example.weatherapp.externalapi;

import com.example.weatherapp.externalapi.model.ExternalApiResponse;
import com.example.weatherapp.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static java.util.Objects.isNull;

@Component
@Slf4j
@AllArgsConstructor
public class ExternalApiHandler {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static final Double STAVANGER_LONGITUDE = 5.733107;
    static final Double STAVANGER_LATITUDE = 58.969975;
    static final String URL = "https://www.7timer.info/bin/civillight.php?" +
            "lon=" + STAVANGER_LONGITUDE +
            "&lat=" + STAVANGER_LATITUDE +
            "&unit=metric&output=json";

    private final RestTemplate restTemplate;
    private final WeatherService weatherService;

    @PostConstruct
    public void init() {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Scheduled(initialDelay = 0, fixedRate = 60_000)
    public void readExternalApiAndSaveResultToDB() {
        log.info("Starting to read from URL: {}", URL);

        String result = readExternalApi();

        ExternalApiResponse externalApiResponse = mapToExternalApiResponse(result);

        boolean savedSuccessfully = weatherService.saveDataToDB(externalApiResponse);
        if (savedSuccessfully) {
            log.info("External API result successfully saved to database");
        }
    }

    private String readExternalApi() {
        String result = restTemplate.getForObject(URL, String.class);
        log.debug("External API returned: {}", result);

        if (isNull(result)) {
            log.error("Received null result when reading external API from ULR: {}", URL);
            throw new IllegalStateException("Received null when reading external API");
        }
        return result;
    }

    private ExternalApiResponse mapToExternalApiResponse(String externalApiResult) {
        try {
            ExternalApiResponse externalApiResponse = OBJECT_MAPPER.readValue(externalApiResult, ExternalApiResponse.class);
            log.debug("External API result mapped to: {}", externalApiResponse);
            return externalApiResponse;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error happened while processing JSON from external API", e);
        }
    }
}
