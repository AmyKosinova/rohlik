package com.rohlik.rohlik.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "rohlik")
public class RohlikProperties {

    private Order order;

    @Data
    public static class Order {
        private Long expirySeconds;
    }
}


