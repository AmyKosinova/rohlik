package com.rohlik.rohlik.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rohlik")
public class RohlikProperties {

    public Order order;


    public static class Order {
        public Long expirySeconds;
    }
}


