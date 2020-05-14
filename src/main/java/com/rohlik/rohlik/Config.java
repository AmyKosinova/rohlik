package com.rohlik.rohlik;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class Config {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public ThreadPoolExecutor threadPoolExecutor() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newSingleThreadScheduledExecutor().schedule(
                (Runnable) () -> {

                }, 1, TimeUnit.MINUTES
        );
        return executor;
    }

}
