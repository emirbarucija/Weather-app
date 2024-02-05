package com.example.weatherapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${rest.template.connect.timeout.millis}")
    private Long restTemplateConnectTimeoutMillis;
    @Value("${rest.template.read.timeout.millis}")
    private Long restTemplateReadTimeoutMillis;

    @Value("${thread.pool.task.executor.core.pool.size}")
    private Integer taskExecutorCorePoolSize;
    @Value("${thread.pool.task.executor.max.pool.size}")
    private Integer taskExecutorMaxPoolSize;
    @Value("${thread.pool.task.executor.queue.capacity}")
    private Integer taskExecutorQueueCapacity;
    @Value("${thread.pool.task.executor.keep.alive.seconds}")
    private Integer taskExecutorKeepAliveSeconds;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(restTemplateConnectTimeoutMillis))
                .setReadTimeout(Duration.ofMillis(restTemplateReadTimeoutMillis))
                .build();
    }

    @Bean
    public ThreadPoolTaskExecutor weatherControllerThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(taskExecutorCorePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(taskExecutorMaxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(taskExecutorQueueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(taskExecutorKeepAliveSeconds);
        return threadPoolTaskExecutor;
    }
}
