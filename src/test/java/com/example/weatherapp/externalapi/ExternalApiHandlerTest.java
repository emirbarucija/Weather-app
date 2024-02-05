package com.example.weatherapp.externalapi;

import com.example.weatherapp.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static com.example.weatherapp.externalapi.ExternalApiHandler.URL;
import static com.example.weatherapp.util.TestsFactory.createJsonString;
import static com.example.weatherapp.util.TestsFactory.expectedExternalApiResponse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExternalApiHandlerTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private ExternalApiHandler externalApiHandler;

    @Test
    public void given_externalApiReturnsJsonString_when_readExternalApiAndSaveResultToDB_then_generateProperExternalApiResponse() {
        // given
        when(restTemplate.getForObject(URL, String.class)).thenReturn(createJsonString());

        // when
        externalApiHandler.readExternalApiAndSaveResultToDB();

        // then
        verify(restTemplate, times(1)).getForObject(URL, String.class);
        verify(weatherService, times(1)).saveDataToDB(expectedExternalApiResponse());
    }

    @Test
    public void given_externalApiThrowsException_when_readExternalApiAndSaveResultToDB_then_weatherServiceIsNotInvoked() {
        // given
        when(restTemplate.getForObject(URL, String.class)).thenThrow(new RuntimeException("Something went wrong"));

        // when
        Executable executable = () -> externalApiHandler.readExternalApiAndSaveResultToDB();

        // then
        assertThrows(RuntimeException.class, executable);
        verify(weatherService, never()).saveDataToDB(any());
    }

    @Test
    public void given_externalApiReturnsNull_when_readExternalApiAndSaveResultToDB_then_IllegalStateExceptionIsThrown_and_weatherServiceIsNotInvoked() {
        // given
        when(restTemplate.getForObject(URL, String.class)).thenReturn(null);

        // when
        Executable executable = () -> externalApiHandler.readExternalApiAndSaveResultToDB();

        // then
        assertThrows(IllegalStateException.class, executable);
        verify(weatherService, never()).saveDataToDB(any());
    }
}
