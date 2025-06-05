package com.rohlik.rohlik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class RohlikApplication {

    public static void main(String[] args) {
        SpringApplication.run(RohlikApplication.class, args);
    }

}
