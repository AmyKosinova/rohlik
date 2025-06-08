package com.rohlik.rohlik;

import com.rohlik.rohlik.properties.RohlikProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(RohlikProperties.class)
public class RohlikApplication {

    public static void main(String[] args) {
        SpringApplication.run(RohlikApplication.class, args);
    }

}
